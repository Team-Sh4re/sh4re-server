package share.sh4re.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "code", cascade = CascadeType.ALL)
    private List<Like> likeList = new ArrayList<>();

    @ManyToOne
    @Setter
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

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