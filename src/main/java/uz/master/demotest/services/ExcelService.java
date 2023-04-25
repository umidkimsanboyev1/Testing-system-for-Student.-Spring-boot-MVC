package uz.master.demotest.services;

import com.poiji.bind.Poiji;
import org.springframework.stereotype.Service;
import uz.master.demotest.dto.excel.QuestionsExcel;

import java.io.File;
import java.util.List;

@Service
public class ExcelService {

    public void convertExcelToEntity(){
        File file = new File("D:/JavaProject/tets/AtomsProject/src/main/resources/files/1682425537361.xlsx");
        List<QuestionsExcel> questions = Poiji.fromExcel(file, QuestionsExcel.class);
        System.out.println(questions);
    }
}
