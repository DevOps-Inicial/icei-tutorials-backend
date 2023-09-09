package link.grooverdev.web.api.tutorials.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TutorialDto implements Serializable {

    private static final long serialUID = 1L;

    private long id;
    private String title;
    private String description;
    private boolean published;
    private boolean enabled;
}
