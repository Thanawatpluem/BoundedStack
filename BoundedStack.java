import java.util.ArrayList;
import java.util.List;
/**
 * BoundedStack คือสแตกรีวิวหนังที่มีขนาดจำกัดตาม capacity
 * ธนวัต บุญชูยิ่ง 6821651345 801 , ชลธิฌา แตงเขียว 6821651132 801
 */
public class BoundedStack {
    private final List<MovieReview> movieReviews;
    private final int capacity;
    // Abstraction Function:
    // AF(moviereviews,capacity) = สแตกรีวิวหนัังที่เก็บได้ไม่เกินcapacity

    // Representation Invariant:
    //   1. moviereviewsต้องไม่เป็นnull
    //   2. capacityต้องมีอย่างน้อย1
    //   3. จำนวนรีวิวต้องอยู่ระหว่าง 0 ถึง capacity
    //   4. moviereviewsต้องสมาชิกที่เป็นnull
    //   5. reviewsทุกตัวต้องมีชื่อหนังและข้อความreviews 

    // Safety from rep exposure:
    // movieReviews เป็น private final และไม่ถูกส่งออกให้ client โดยตรง
    // MovieReview เป็น immutable value object

    private void checkRep() {
        assert movieReviews != null : "movieReviews must not be null";
        assert capacity >= 1 : "capacity must be at least 1";
        assert movieReviews.size() <= capacity : "stack size must <= capacity";

        for (MovieReview review : movieReviews) {
            assert review != null : "movieReviews must not contain null";
            assert !review.getMovieTitle().isEmpty() : "movie title must not be blank";
            assert !review.getReviewText().isEmpty() : "review text must not be blank";
        }
    }

    /**
     * @param capacity จำนวนรีวิวสูงสุด ต้องมีค่าอย่างน้อย 1
     * @throws IllegalArgumentException ถ้า capacity น้อยกว่า 1
     */
    public BoundedStack(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity ต้องมีค่าอย่างน้อย 1");
        }
        this.capacity = capacity;
        this.movieReviews = new ArrayList<>(capacity);
        checkRep();
    }

    /**
     * @param movieTitle ชื่อหนังต้องไม่เป็น null ว่าง หรือมีแต่ช่องว่าง
     * @param reviewText ข้อความรีวิวต้องไม่เป็น null ว่าง หรือมีแต่ช่องว่าง
     * @return true ถ้าเพิ่มสำเร็จ หรือ false ถ้าสแตกเต็มอยู่แล้ว
     * @throws IllegalArgumentException ถ้าชื่อหนังหรือข้อความรีวิวไม่ถูกต้อง
     */
    public boolean push(String movieTitle, String reviewText) {
        MovieReview newReview = new MovieReview(movieTitle, reviewText);
        if (isFull()) {
            return false;
        }
        movieReviews.add(newReview);
        checkRep();
        return true;
    }

    /**
     * @return รีวิวล่าสุดที่ยังไม่ถูกนำออก
     * @throws IllegalStateException ถ้าสแตกว่าง
     */
    public MovieReview pop() {
        if (isEmpty()) {
            throw new IllegalStateException("can not pop stack empty");
        }
        MovieReview latestReview = movieReviews.remove(movieReviews.size() - 1);
        checkRep();
        return latestReview;
    }

    /**
     * @return รีวิวล่าสุดที่ยังไม่ถูกนำออก
     * @throws IllegalStateException ถ้าสแตกว่าง
     */
    public MovieReview peek() {
        if (isEmpty()) {
            throw new IllegalStateException("can not peek stack empty");
        }
        checkRep();
        return movieReviews.get(movieReviews.size() - 1);
    }

    /** @return จำนวนรีวิวที่อยู่ในสแตกขณะนี้ */
    public int size() {
        checkRep();
        return movieReviews.size();
    }

    /** @return true ถ้ายังไม่มีรีวิวอยู่ในสแตก */
    public boolean isEmpty() {
        checkRep();
        return movieReviews.isEmpty();
    }

    /** @return true ถ้าจำนวนรีวิวเท่ากับความจุสูงสุดแล้ว */
    public boolean isFull() {
        checkRep();
        return movieReviews.size() == capacity;
    }

    /** @return จำนวนรีวิวสูงสุดที่สแตกนี้เก็บได้ */
    public int capacity() {
        checkRep();
        return capacity;
    }

    /**
     * @return สแตกรีวิวสำเนาที่เป็นอิสระจากต้นฉบับ
     */
    public BoundedStack copy() {
        BoundedStack copiedStack = new BoundedStack(capacity);
        copiedStack.movieReviews.addAll(movieReviews);
        copiedStack.checkRep();
        return copiedStack;
    }

    /**
     * @return สแตกรีวิวใหม่ที่มีลำดับกลับด้าน
     */
    public BoundedStack reversed() {
        BoundedStack reversedStack = new BoundedStack(capacity);
        for (int index = movieReviews.size() - 1; index >= 0; index--) {
            reversedStack.movieReviews.add(movieReviews.get(index));
        }
        reversedStack.checkRep();
        return reversedStack;
    }

    public String toString() {
        return movieReviews + " (ขวาสุด = รีวิวบนยอดสแตก)";
    }

    /**
     * ข้อมูลหนึ่งรีวิว ประกอบด้วยชื่อหนังและข้อความรีวิว
     * ออบเจ็กต์นี้แก้ไขไม่ได้หลังสร้าง
     */
    public static final class MovieReview {
        private final String movieTitle;
        private final String reviewText;
        private MovieReview(String movieTitle, String reviewText) {
            if (movieTitle == null || movieTitle.isEmpty()) {
                throw new IllegalArgumentException("movieTitle ต้องไม่เป็น null ว่าง หรือมีแต่ช่องว่าง");
            }
            if (reviewText == null || reviewText.isEmpty()) {
                throw new IllegalArgumentException("reviewText ต้องไม่เป็น null ว่าง หรือมีแต่ช่องว่าง");
            }
            this.movieTitle = movieTitle;
            this.reviewText = reviewText;
        }

        /** @return ชื่อหนังของรีวิวนี้ */
        public String getMovieTitle() {
            return movieTitle;
        }

        /** @return ข้อความรีวิวหนัง */
        public String getReviewText() {
            return reviewText;
        }
        /** @return ชื่อหนังและข้อความรีวิวหนัง*/
        public String toString() {
            return movieTitle + ": " + reviewText;
        }
    }
}