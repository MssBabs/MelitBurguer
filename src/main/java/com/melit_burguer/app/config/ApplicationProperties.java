package com.melit_burguer.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Melit Burguer.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
  String uploadDir;

  public String getUploadDir(){
    return uploadDir;
  }
  public void setUploadDir (String uploadDir){
    this.uploadDir = uploadDir;
  }
}
