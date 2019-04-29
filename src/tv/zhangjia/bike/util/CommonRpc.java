package tv.zhangjia.bike.util;

import java.util.Random;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/*
pom.xml
<dependency>
  <groupId>com.aliyun</groupId>
  <artifactId>aliyun-java-sdk-core</artifactId>
  <version>4.0.3</version>
</dependency>
*/
public class CommonRpc {
	public static void main(String[] args) {
		new CommonRpc().sendCode("15666335517");
	}

	public String sendCode(String tel) {
		DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIlsCaVRpjMFVB",
				"yGg77K38ZRLa1pOmlRfkHB8A5k4ySZ");
		IAcsClient client = new DefaultAcsClient(profile);

		CommonRequest request = new CommonRequest();
		request.setMethod(MethodType.POST);
		request.setDomain("dysmsapi.aliyuncs.com");
		request.setVersion("2017-05-25");
		request.setAction("SendSms");
		request.putQueryParameter("PhoneNumbers", tel);
		request.putQueryParameter("SignName", "Ð¡ÂÌµ¥³µ");
		request.putQueryParameter("TemplateCode", "SMS_164510483");
		String random = String.valueOf(new Random().nextInt(9999) + 100);
		request.putQueryParameter("TemplateParam", "{\"code\":\"" + random + "\"}");
		try {
			CommonResponse response = client.getCommonResponse(request);
			System.out.println(response.getData());
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return random;
	}
}