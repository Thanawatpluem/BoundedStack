import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
/**
 * bounded stack การจัดอันดับหนังในค่ายMarvelที่คุณชื่นชอบ 10อันดับแรก
 */
public class BoundedStack {
    private final List<String>Movies;
    public static final int MAX_MOVIES = 100;

    //   AF(Movies) = ลำดับของหนังใน tier list ที่ผู้ใช้จัดไว้ โดย Movies  
    //               เป็น List ของชื่อหนัง (String) ที่ไม่ซ้ำกันและไม่เกิน MAX_MOVIES

    // Representation Invariant:
    //   ต้องมีรายการหนังอยู่จริง (ไม่เป็น null)
    //   ไม่มีหนังใดเป็น null
    //   ไม่มีชื่อหนังที่เป็นสตริงว่าง
    //   ชื่อหนังห้ามซ้ำกัน
    //   มีได้ไม่เกิน MAX_MOVIES (100) หนัง

    public BoundedStack() {
        this.Movies = new ArrayList<>();
        checkRep();
    }

    private void checkRep() {
        assert Movies != null : "Movies must not be null";
        assert Movies.size() <=MAX_MOVIES : "Movies size must not exceed Mex_Moview";
        Set<String> seen = new HashSet<>();

        for (String M : Movies) {
            assert M != null : "Movies must not be null";
            assert !M.isEmpty() : "Movies must not be Empty";
            assert seen.add(M): "ชื่อหนังซ้ำ";

        }


        }
    }



