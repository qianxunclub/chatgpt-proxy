package com.qianxunclub.chatgptproxy.openai;

import com.qianxunclub.chatgptproxy.configuration.AppProperties;
import com.qianxunclub.chatgptproxy.configuration.CoreException;
import com.qianxunclub.chatgptproxy.openai.entity.BaseResponse;
import com.qianxunclub.chatgptproxy.openai.entity.ChatCompletion;
import com.qianxunclub.chatgptproxy.openai.entity.ChatCompletionResponse;
import com.qianxunclub.chatgptproxy.openai.entity.ImageRequest;
import com.qianxunclub.chatgptproxy.openai.entity.ImageResponse;
import com.qianxunclub.chatgptproxy.openai.entity.SubscriptionData;
import com.qianxunclub.chatgptproxy.openai.entity.UseageResponse;
import com.qianxunclub.chatgptproxy.util.JSON;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class OpenAiApi {

    private final AppProperties appProperties;

    public <R> R execute(HttpServletRequest request, String uri, HttpMethod method, Type returnType) throws CoreException {
        return this.execute(request, uri, method, null, returnType);
    }

    public <R> R execute(HttpServletRequest request, String uri, HttpMethod method, String jsonBody, Type returnType) throws CoreException {
        String url = appProperties.getApiHost().concat(uri);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(appProperties.getTimeout(), TimeUnit.SECONDS)
                .writeTimeout(appProperties.getTimeout(), TimeUnit.SECONDS)
                .readTimeout(appProperties.getTimeout(), TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = null;
        if (StringUtils.hasLength(jsonBody)) {
            requestBody = RequestBody.create(jsonBody, okhttp3.MediaType.parse(MediaType.APPLICATION_JSON_VALUE));
        }
        String openAIKey = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasLength(openAIKey)) {
            openAIKey = appProperties.getOpenaiKey();
        }
        if (!StringUtils.hasLength(openAIKey)) {
            throw new CoreException("apikey错误");
        }

        Request req = new Request.Builder()
                .url(url)
                .method(method.name(), requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + openAIKey)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:109.0) Gecko/20100101 Firefox/115.0")
                .build();
        try {
            Response response = okHttpClient.newCall(req).execute();
            String body = response.body().string();
            if (response.isSuccessful()) {
                return JSON.getGson().fromJson(body, returnType);
            } else {
                BaseResponse baseResponse = JSON.getGson().fromJson(body, BaseResponse.class);
                if (Objects.nonNull(baseResponse.getError())) {
                    throw new CoreException(JSON.getGson().toJson(baseResponse.getError()));
                }
                return null;
            }
        } catch (Exception e) {
            throw new CoreException(e.getMessage());
        }
    }


    /**
     * chat
     */
    public ChatCompletionResponse chatCompletion(HttpServletRequest request, ChatCompletion chatCompletion) {
        String uri = "v1/chat/completions";
        return this.execute(request, uri, HttpMethod.POST, JSON.getGson().toJson(chatCompletion), ChatCompletionResponse.class);
    }


    /**
     * 余额查询
     */
    public SubscriptionData subscription(HttpServletRequest request) {
        String uri = "v1/dashboard/billing/subscription";
        return this.execute(request, uri, HttpMethod.GET, SubscriptionData.class);
    }


    /**
     * 时间段使用金额
     */
    public UseageResponse usage(HttpServletRequest request, String startDate, String endDate) {
        String uri = "v1/dashboard/billing/usage?start_date=" + startDate + "&end_date=" + endDate;
        return this.execute(request, uri, HttpMethod.GET, UseageResponse.class);
    }

    public ImageResponse imageGenerations(HttpServletRequest request, ImageRequest imageRequest) {
        String uri = "v1/images/generations";
        return this.execute(request, uri, HttpMethod.POST, JSON.getGson().toJson(imageRequest), ImageResponse.class);
    }

}
