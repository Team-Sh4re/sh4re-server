package share.sh4re.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
public class Code extends Base {
    public enum Fields {
        PYTHON, WEB
    }

    @NotNull
    private Long likes;

    @NotNull
    private Long views;

    @NotNull
    @Column(length=32768)
    private String code;

    @NotNull
    private String title;

    @NotNull
    @Column(length=32768)
    private String description;

    @NotNull
    private Fields field;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id")
    private User user;

    public void update(String title, String description, String code, Fields field, User user){
        this.likes = 0L;
        this.views = 0L;
        this.code = code;
        this.title = title;
        this.description = description;
        this.field = field;
        this.user = user;
    }

    public void increaseLikes() {
        this.likes++;
    }

    public void decreaseLikes() {
        if (this.likes > 0) {
            this.likes--;
        }
    }
}