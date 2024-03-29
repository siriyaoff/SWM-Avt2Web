package com.avatar2.home.web.dto;

import com.avatar2.home.domain.inference.PElem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class InferenceResponseDto {
    private ArrayList<PElem> coord = new ArrayList<>();
    private ArrayList<PElem> eyelash = new ArrayList<>();
    private byte[] iris;
    private String err;

    @Builder
    public InferenceResponseDto(ArrayList<PElem> coord, ArrayList<PElem> eyelash, byte[] iris, String err) {
        this.coord = new ArrayList<>();
        for (PElem p : coord) {
            this.coord.add(new PElem(p.getId(), p.getX(), p.getY()));
        }
        this.eyelash = new ArrayList<>();
        for (PElem p : eyelash) {
            this.eyelash.add(new PElem(p.getId(), p.getX(), p.getY()));
        }
        this.iris = iris;
        this.err = err;
    }
}
