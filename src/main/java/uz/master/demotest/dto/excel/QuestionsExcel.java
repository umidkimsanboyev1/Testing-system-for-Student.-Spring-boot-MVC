package uz.master.demotest.dto.excel;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class QuestionsExcel {
    @ExcelRow
    private Long number;

    @ExcelCellName("Savol")
    private String text;

    @ExcelCellName("To'g'ri javob")
    private String correctAnswer;

    @ExcelCellName("Noto'g'ri javob1")
    private String answer2;

    @ExcelCellName("Noto'g'ri javob2")
    private String answer3;

    @ExcelCellName("Noto'g'ri javob3")
    private String answer4;

    private List<String> otherAnswers = new ArrayList<>();

    public void toList(){
        this.otherAnswers.add(answer2);
        this.otherAnswers.add(answer3);
        this.otherAnswers.add(answer4);
    }
}
