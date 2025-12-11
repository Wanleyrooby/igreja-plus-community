package com.igrejaplus.dto;

import com.igrejaplus.model.Church;
import java.time.Instant;

public record WorshipSongRequest(

         String title,
         String author,
         String tone,

        
         String lyrics,

         String tags
) {
}
