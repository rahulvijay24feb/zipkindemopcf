package com.cvs.pcfdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import brave.Tracing;
import brave.opentracing.BraveTracer;
import brave.sampler.Sampler;
import zipkin.Span;
import zipkin.reporter.AsyncReporter;
import zipkin.reporter.Encoding;
import zipkin.reporter.okhttp3.OkHttpSender;

@SpringBootApplication
public class PcfdemoApplication {

	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}
	@Bean
	public io.opentracing.Tracer zipkinTracer() {
		OkHttpSender okHttpSender = OkHttpSender.builder()
				.encoding(Encoding.JSON)
				.endpoint("http://localhost:9411/api/v1/spans")
				.build();
		AsyncReporter<Span> reporter = AsyncReporter.builder(okHttpSender).build();
		Tracing braveTracer = Tracing.newBuilder()
				.localServiceName("spring-boot")
				.reporter(reporter)
				.traceId128Bit(true)
				.sampler(Sampler.ALWAYS_SAMPLE)
				.build();
		return BraveTracer.create(braveTracer);
	}
	public static void main(String[] args) {
		SpringApplication.run(PcfdemoApplication.class, args);
	}
}
