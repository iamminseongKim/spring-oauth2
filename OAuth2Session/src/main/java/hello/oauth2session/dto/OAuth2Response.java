package hello.oauth2session.dto;

/**
 * naver, google 등 다양한 인증기관을 받기 위한 껍데기
 * */
public interface OAuth2Response {

    SiteType getProvider();
    
    String getProviderId();
    
    String getEmail();
    
    String getName();

}
