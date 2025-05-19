package share.sh4re.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import share.sh4re.dto.req.ChatGPTRequest;
import share.sh4re.dto.res.ChatGPTResponse;
import share.sh4re.exceptions.errorcode.OpenAIErrorCode;

@Service
@Transactional
@RequiredArgsConstructor
public class OpenAiService {
  @Value("${openai.model}")
  private String model;

  @Value("${openai.api.url}")
  private String apiURL;

  private final RestTemplate template;

  private String generate(String prompt){
    if(prompt == null || prompt.isEmpty()) throw OpenAIErrorCode.INVALID_ARGUMENT.defaultException();
    ChatGPTRequest chatGPTRequest = new ChatGPTRequest(model, prompt);
    ChatGPTResponse chatGPTResponse;
    try {
       chatGPTResponse = template.postForObject(apiURL, chatGPTRequest, ChatGPTResponse.class);
    } catch (Exception e) {
      throw OpenAIErrorCode.FAILED_TO_GENERATE.defaultException(e);
    }
    if(chatGPTResponse == null) throw OpenAIErrorCode.FAILED_TO_GENERATE.defaultException();
    String result = chatGPTResponse.output().get(0).content().get(0).text();
    return result == null ? "" : result.trim();
  }

  public String generateDescription(@Valid String code){
    String result = generate(
        """
            당신은 코드 분석 전문가입니다.
            아래의 코드를 보고 코드의 "역할", "효율", "특징"을 분석하세요.
            
            예시
            print("hello World")
            -> hello World를 출력하는 프로그램입니다.
            s = int(input())
            for i in range(1, s):
              s += i
            print(s)
            -> s값을 입력받아 for문을 통해 1부터 s값까지의 합을 출력하는 코드입니다
            
            위의 예시를 참고해 비슷한 형식으로 코드에 대한 분석 결과를 만들어줘.
            역할, 효율, 특징이 없으면 없다는 말은 안해도 돼.
            입력값을 뭐로 받아서 뭐를 출력한다 이런 식으로.
            입력값: s, 출력값: n 이런식으로 틀에 꽉 맞춰질 필요까지는 없고 어느 정도의 자유도는 있어야 해.
            가장 중요한건 중요한 부분은 포함하되, 설명이 길어지면 요약해서 간단하게 답해야해.
            분석 결과만 말해줘 부가적인 인사나 질문같은건 필요 없어.
            
            코드:
            
            """ + code
    );
    if(result.isEmpty()) throw OpenAIErrorCode.FAILED_TO_GENERATE.defaultException();
    return result;
  }
}
