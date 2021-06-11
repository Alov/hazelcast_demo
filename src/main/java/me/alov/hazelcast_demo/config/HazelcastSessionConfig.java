package me.alov.hazelcast_demo.config;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSession;
import org.springframework.session.hazelcast.*;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

@Configuration
@EnableHazelcastHttpSession
public class HazelcastSessionConfig {


//    @Bean
//    public HazelcastInstance hazelcastInstance() {
//        Config config = new Config();
//
//
//        NetworkConfig networkConfig = config.getNetworkConfig();
//        JoinConfig join = networkConfig.getJoin();
//        join.getMulticastConfig().setEnabled(false);
//
//        TcpIpConfig tcpIpConfig = join.getTcpIpConfig();
//        tcpIpConfig.setEnabled(true);
//        tcpIpConfig.addMember("localhost:5701");
//        config.setInstanceName("dev");
//
//        MapAttributeConfig attributeConfig = new MapAttributeConfig()
//                .setName(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
//                .setExtractor(PrincipalNameExtractor.class.getName());
//        config.getMapConfig(HazelcastSessionRepository.DEFAULT_SESSION_MAP_NAME)
//                .addMapAttributeConfig(attributeConfig).addMapIndexConfig(
//                new MapIndexConfig(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE, false));
//
//        config.getManagementCenterConfig()
//                .setEnabled(true)
//                .setUpdateInterval(1)
//                .setUrl("http://192.168.0.102:9191/mancenter");
//        return Hazelcast.newHazelcastInstance(config);
//    }

    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = new Config();


        NetworkConfig networkConfig = config.getNetworkConfig();
        JoinConfig join = networkConfig.getJoin();
        join.getMulticastConfig().setEnabled(false);

        TcpIpConfig tcpIpConfig = join.getTcpIpConfig();
        tcpIpConfig.setEnabled(true);
        tcpIpConfig.addMember("localhost:5701");
        config.setInstanceName("dev");

        AttributeConfig attributeConfig = new AttributeConfig()
                .setName(Hazelcast4IndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
                .setExtractorClassName(Hazelcast4PrincipalNameExtractor.class.getName());
        config.getMapConfig(Hazelcast4IndexedSessionRepository.DEFAULT_SESSION_MAP_NAME)
                .addAttributeConfig(attributeConfig).addIndexConfig(
                new IndexConfig(IndexConfig.DEFAULT_TYPE, Hazelcast4IndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE));
        SerializerConfig serializerConfig = new SerializerConfig();
        serializerConfig.setImplementation(new HazelcastSessionSerializer()).setTypeClass(MapSession.class);
        config.getSerializationConfig().addSerializerConfig(serializerConfig);
        return Hazelcast.newHazelcastInstance(config);

    }

}
