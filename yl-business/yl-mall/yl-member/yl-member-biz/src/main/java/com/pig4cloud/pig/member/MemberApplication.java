package com.pig4cloud.pig.member;

import com.pig4cloud.pig.common.security.annotation.EnablePigFeignClients;
import com.pig4cloud.pig.common.security.annotation.EnablePigResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author pig archetype
 * <p>
 * 项目启动类
 */
@EnablePigFeignClients
@EnablePigResourceServer
@SpringCloudApplication
public class MemberApplication {
	public static void main(String[] args) {
		SpringApplication.run(MemberApplication.class, args);
	}
}
