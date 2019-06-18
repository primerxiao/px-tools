package pxt.bean.esbToJavaFile;

import lombok.Data;
import lombok.ToString;

/**
 * @author primerxiao
 * @date 2019/5/31
 */
@Data
@ToString
public class Result{
    public boolean merged;
    public int startRow;
    public int endRow;
    public int startCol;
    public int endCol;
    public Result(boolean merged,int startRow,int endRow
            ,int startCol,int endCol){
        this.merged = merged;
        this.startRow = startRow;
        this.endRow = endRow;
        this.startCol = startCol;
        this.endCol = endCol;
    }

}
