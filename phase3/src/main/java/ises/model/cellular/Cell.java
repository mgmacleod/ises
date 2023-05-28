package ises.model.cellular;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cell {
    private Genome genome;
    private Proteome proteome;
    private Metabolome metabolome;
}
