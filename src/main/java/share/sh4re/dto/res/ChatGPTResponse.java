package share.sh4re.dto.res;


import java.util.List;

public record ChatGPTResponse(List<Output> output) {
  public record Output(List<Content> content) {
      public record Content(String text) {}
  }
}