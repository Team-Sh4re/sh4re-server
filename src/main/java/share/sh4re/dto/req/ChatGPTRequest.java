package share.sh4re.dto.req;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ChatGPTRequest {
  private final String model;
  private final String input;
}