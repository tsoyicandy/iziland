package com.itiad.iziland.util;

import com.itiad.iziland.models.entities.Bien;
import com.itiad.iziland.models.entities.FileInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class BienImage {
    Bien bien;
    List<String> listImage;
}
