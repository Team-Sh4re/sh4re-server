package share.sh4re.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Code {
    public enum Fields {
        PYTHON, WEB
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long likes;
    private Long views;
    private String file;
    private String title;
    private String description;
    private Fields field;
    @ManyToOne
    private User user;

    public void update(String title, String description, String file, Fields field, User user){
        this.likes = 0L;
        this.views = 0L;
        this.file = file;
        this.title = title;
        this.description = description;
        this.field = field;
        this.user = user;
    }
}