import java.util.Map;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class ClusterSumStormTopology {

    /**
     * Spout需要继承BaseRichSpout
     * 产生数据并且发送出去
     * */
    public static class DataSourceSpout extends BaseRichSpout{

        private SpoutOutputCollector collector;
        /**
         * 初始化方法，在执行前只会被调用一次
         * @param conf 配置参数
         * @param context 上下文
         * @param collector 数据发射器
         * */
        @Override
        public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
            this.collector = collector;
        }

        int number = 0;
        /**
         * 产生数据，生产上一般是从消息队列中获取数据
         * */
        @Override
        public void nextTuple() {
            this.collector.emit(new Values(++number));
            System.out.println("spout发出："+number);
            Utils.sleep(1000);
        }

        /**
         * 声明输出字段
         * @param declarer
         * */
        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            /**
             * num是上nextTuple中emit中的new Values对应的。上面发几个，这里就要定义几个字段。
             * 在bolt中获取的时候，只需要获取num这个字段就行了。
             * */
            declarer.declare(new Fields("num"));
        }

    }

    /**
     * 数据的累计求和Bolt
     * 接收数据并且处理
     * */
    public static class SumBolt extends BaseRichBolt{
        /**
         * 初始化方法，只会被执行一次
         * */
        @Override
        public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

        }
        int sum=0;
        /**
         * 获取spout发送过来的数据
         * */
        @Override
        public void execute(Tuple input) {
            //这里的num就是在spout中的declareOutputFields定义的字段名
            //可以根据index获取，也可以根据上一个环节中定义的名称获取
            Integer value = input.getIntegerByField("num");
            sum+=value;
            System.out.println("Bolt:sum="+sum);
        }
        /**
         * 声明输出字段
         * @param declarer
         * */
        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
        }
    }

    public static void main (String[] args){
        //TopologyBuilder根据spout和bolt来构建Topology
        //storm中任何一个作业都是通过Topology方式进行提交的
        //Topology中需要指定spout和bolt的执行顺序
        TopologyBuilder tb = new TopologyBuilder();
        tb.setSpout("DataSourceSpout", new DataSourceSpout());
        //SumBolt以随机分组的方式从DataSourceSpout中接收数据
        tb.setBolt("SumBolt", new SumBolt()).shuffleGrouping("DataSourceSpout");
        //代码提交到storm集群上运行
        try {
            StormSubmitter.submitTopology("ClusterSumStormTopology", new Config(), tb.createTopology());
        } catch (AlreadyAliveException e) {
            e.printStackTrace();
        } catch (InvalidTopologyException e) {
            e.printStackTrace();
        } catch (AuthorizationException e) {
            e.printStackTrace();
        }

    }
}