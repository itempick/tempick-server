package com.tempick.tempickserver.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val visitorLogInterceptor: VisitorLogInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(visitorLogInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/admin/**") // Exclude admin endpoints
            .excludePathPatterns("/swagger-ui/**")
            .excludePathPatterns("/v3/api-docs/**")
    }
}
