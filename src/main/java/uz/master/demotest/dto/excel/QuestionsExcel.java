package uz.master.demotest.dto.excel;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuestionsExcel {
    @ExcelRow
    private Long number;

    @ExcelCellName("question")
    private String text;

    @ExcelCellName("correct")
    private String correctAnswer;

    @ExcelCellName("answer2")
    private String answer2;

    @ExcelCellName("answer3")
    private String answer3;

    @ExcelCellName("answer4")
    private String answer4;
}
