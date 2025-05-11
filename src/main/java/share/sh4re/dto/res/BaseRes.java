package share.sh4re.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseRes<T> {
  private final boolean ok;
  private final T data;
}

