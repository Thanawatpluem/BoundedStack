import java.util.ArrayList;
import java.util.List;

public class BoundedStack {

    private final List<MovieReview> movieReviews;
    private final int capacity;
    // TODO 1: เขียน Abstraction Function ตรงนี้
    // Abstraction Function:
    //   AF(moviereviews,capacity) = สแตกรีวิวหนัังที่เก็บได้ไม่เกินcapacity3

    // TODO 2: เขียน Representation Invariant ตรงนี้ (4 ข้อ)
    // Representation Invariant:
    //   1. moviereviewsต้องไม่เป็นnull
    //   2.capacityต้องมีอย่างน้อย1
    //  3. จำนวนรีวิวต้องอยู่ระหว่าง 0 ถึง capacity
    //   4.moviereviewsต้องสมาชิกที่เป็นnull
    //   5.reviewsทุกตัวต้องมีชื่อหนังและข้อความreviews 

    // TODO 3: เขียน Safety from rep exposure ตรงนี้
    // Safety from rep exposure:
    //   movieReviews เป็น private final และไม่ถูกส่งออกให้ client โดยตรง
    // MovieReview เป็น immutable value object


    private void checkRep() {
        assert movieReviews != null : "movieReviews must not be null";
        assert capacity >= 1 : "capacity must be at least 1 ";
        assert movieReviews.size() <= capacity : "movieReviews must <= capacity ";
        for (MovieReview review : movieReviews){
            assert review != null : "reviews must be null";
            assert !review.getMovieTitle().isBlank() : "movie title must not be empty";
            assert !review.getReviewText().isBlank() : "reviews text must not be empty";

        }

    }


    public BoundedStack(int capacity) {
        if (capacity < 1 ) {
            throw new IllegalArgumentException ("capacity must be at least 1");
        } 
        this.capacity = capacity ;
        this.movieReviews = new ArrayList<>(capacity);
        checkRep();
      
    }

    public boolean push(String movieTitle, String reviewText) {
        MovieReview newReview = new MovieReview (movieTitle,reviewText);
        if (isFull()) {
            return false ;
        }
        movieReviews.add(newReview);
        checkRep();
        return true;
    }

    public MovieReview pop() {
        if (isEmpty()) {
            throw new IllegalArgumentException ("can not pop ");
        }
        MovieReview latestReview = movieReviews.remove(movieReviews.size() -1 );
        checkRep();
        return latestReview ; 
    }

    public MovieReview peek() {
        if (isEmpty()) {
            throw new IllegalArgumentException("can not peek");
        }
        checkRep();
        return movieReviews.get(movieReviews.size() -1 );

    }

    public int size() {
        checkRep();
        return movieReviews.size();
    }

    public boolean isEmpty() {
       checkRep();
       return movieReviews.isEmpty();
    }

    public boolean isFull() {
        checkRep();
        return movieReviews.size()==capacity;
    }

    public int capacity() {
        checkRep();
        return capacity;

    }

    public BoundedStack copy() {
        BoundedStack copiedStack = new BoundedStack(capacity);
        copiedStack.movieReviews.addAll(movieReviews);
        copiedStack.checkRep();
        return copiedStack;

    }

    public BoundedStack reversed() {
        BoundedStack reverStack = new BoundedStack(capacity);
        for (int index = movieReviews.size() -1 ; index >= 0 ; index-- ){
            reverStack.movieReviews.add(movieReviews.get(index));
        }
        return reverStack ;

    }
        public String toString(){
            return movieReviews + "(ขวาสุด = ยอด)";
        }

    public static final class MovieReview {
        
        private final String movieTitle;
        private final String reviewText;

        private MovieReview(String movieTitle, String reviewText) {
         if (movieTitle == null || movieTitle.isBlank()) {
            throw new IllegalArgumentException("movietitle must not be null and Empty");
         }
            if (reviewText == null || reviewText.isBlank()) {
                throw new IllegalArgumentException("reviewText must not be null and Empty");
            }
            this.movieTitle = movieTitle;
            this.reviewText = reviewText;
        }

        public String getMovieTitle() {
            return movieTitle;
        }

        public String getReviewText() {
          return reviewText;
        }

        public boolean equals(Object other) {
            if (this == other ) {
                return true ;
            }
        }

        public String toString() {
              return movieTitle + ":"+reviewText ;
        }
    }
}