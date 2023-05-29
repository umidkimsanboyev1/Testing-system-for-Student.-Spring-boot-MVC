package uz.master.demotest.dto.excel;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StudentsExcelDto {
    @ExcelRow
    private Long number;

    @ExcelCellName("FIO")
    private String fullName;

    @ExcelCellName("Parol")
    private String password;

    @ExcelCellName("Guruh")
    private String groupName;
}
