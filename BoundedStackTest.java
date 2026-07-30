public class BoundedStackTest {
    private static int passed = 0;
    private static int failed = 0;

    private static void check(String testName, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + testName);
        } else {
            failed++;
            System.out.println("[FAIL] " + testName);
        }
    }

    private static boolean sameReview(
        BoundedStack.MovieReview review,String expectedMovieTitle,String expectedReviewText) {
        return review.getMovieTitle().equals(expectedMovieTitle) && review.getReviewText().equals(expectedReviewText);
    }

    public static void main(String[] args) {
        boolean assertionsEnabled = false;
        assert assertionsEnabled = true;
        if (!assertionsEnabled) {
            System.out.println("WARNING: assertions disabled"
                    + " - re-run with: java -ea BoundedStackTest\n");
        }
        System.out.println("=== Movie Review BoundedStack Test Suite ===\n");

        testCreator();
        testPushMovieReview();
        testPopAndPeekMovieReview();
        testProducer();
        testInvalidMovieReviewData();

        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");

        if (failed > 0) {
            System.exit(1);
        }
    }

    // Creator: ความจุปกติ, ค่าขอบเขต 1, และความจุผิดเงื่อนไข
    private static void testCreator() {
        System.out.println("-- Creator --");
        BoundedStack movieReviewStack = new BoundedStack(3);
        check("new moviereview stack starts empty", movieReviewStack.size() == 0 && movieReviewStack.isEmpty());
        check("new stack is not full", !movieReviewStack.isFull());
        check("capacity is the value supplied to the creator", movieReviewStack.capacity() == 3);

        BoundedStack oneReviewStack = new BoundedStack(1);
        check("capacity 1 is valid",oneReviewStack.capacity() == 1 && oneReviewStack.isEmpty()&& !oneReviewStack.isFull());

        boolean ZeroCapacity = false;
        try {
            new BoundedStack(0);
        } catch (IllegalArgumentException exception) {
            ZeroCapacity = true;
        }
        check("capacity 0 is rejected", ZeroCapacity);

        boolean NegativeCapacity = false;
        try {
            new BoundedStack(-5);
        } catch (IllegalArgumentException exception) {
            NegativeCapacity = true;
        }
        check("negative capacity is rejected", NegativeCapacity);
    }

    // Mutator: เพิ่มรีวิวในสแตกว่าง, เติมจนเต็ม, และพยายามเพิ่มเมื่อเต็มแล้ว
    private static void testPushMovieReview() {
        System.out.println("\n-- Push movie reviews --");

        BoundedStack movieReviews = new BoundedStack(3);
        check("push movie title and review text into an empty stack", movieReviews.push("Interstellar", "ดูจบแล้วต้องนั่งคิดอีกสามวัน"));
        check("stack is no longer empty after the first review", !movieReviews.isEmpty() && movieReviews.size() == 1);
        check("the second review leaves the stack one slot from full", movieReviews.push("Cats", "ไม่เข้าใจว่าทำไมถึงมีหนังเรื่องนี้อยู่ได้")
                        && movieReviews.size() == movieReviews.capacity() - 1
                        && !movieReviews.isFull()
                        && sameReview(movieReviews.peek(),"Cats","ไม่เข้าใจว่าทำไมถึงมีหนังเรื่องนี้อยู่ได้"));
        check("the third review fills the stack",movieReviews.push("Spider-Man", "ภาพสวยมาก")
                        && movieReviews.isFull()
                        && movieReviews.size() == movieReviews.capacity());

        check("a fourth review is rejected when the stack is full",!movieReviews.push("Titanic", "เศร้ามาก"));
        check("failed push does not replace the latest review",movieReviews.size() == 3 && sameReview(movieReviews.peek(),"Spider-Man","ภาพสวยมาก"));

        BoundedStack oneReviewStack = new BoundedStack(1);
        check("one review immediately fills a capacity-1 stack",oneReviewStack.push("Dune", "โลกและภาพสวยมาก") && oneReviewStack.isFull());
        check("another review cannot be pushed into a full capacity-1 stack",!oneReviewStack.push("Dune: Part Two", "ภาคต่อที่คุ้มค่าการรอ"));
    }

    // Observer/Mutator: สแตกว่าง, peek, pop และลำดับรีวิวหนัง
    private static void testPopAndPeekMovieReview() {
        System.out.println("\n-- Pop / Peek movie reviews --");

        BoundedStack emptyMovieReviews = new BoundedStack(3);
        boolean rejectedPopFromEmpty = false;
        try {
            emptyMovieReviews.pop();
        } catch (IllegalStateException exception) {
            rejectedPopFromEmpty = true;
        }
        check("pop from an empty movie-review stack is rejected",rejectedPopFromEmpty);
        boolean rejectedPeekFromEmpty = false;
        try {
            emptyMovieReviews.peek();
        } catch (IllegalStateException exception) {
            rejectedPeekFromEmpty = true;
        }
        check("peek at an empty movie-review stack is rejected",rejectedPeekFromEmpty);

        BoundedStack movieReviews = new BoundedStack(3);
        movieReviews.push("Interstellar", "ชวนคิดเรื่องเวลาและครอบครัว");
        movieReviews.push("The Dark Knight", "ตัวร้ายโดดเด่นมาก");
        movieReviews.push("Spider-Man", "สนุกและดูง่าย");

        BoundedStack.MovieReview latestReview = movieReviews.peek();
        check("peek returns the latest movie review without removing it",sameReview(latestReview, "Spider-Man", "สนุกและดูง่าย") && movieReviews.size() == 3);
        check("first pop returns the newest review",sameReview(movieReviews.pop(), "Spider-Man", "สนุกและดูง่าย") && movieReviews.size() == 2 && !movieReviews.isFull());
        check("second pop returns the previous review",sameReview(movieReviews.pop(), "The Dark Knight", "ตัวร้ายโดดเด่นมาก"));
        check("third pop returns the oldest review and empties the stack",sameReview(movieReviews.pop(),"Interstellar","ชวนคิดเรื่องเวลาและครอบครัว") && movieReviews.isEmpty());

        boolean rejectedPopAfterDrain = false;
        try {
            movieReviews.pop();
        } catch (IllegalStateException exception) {
            rejectedPopAfterDrain = true;
        }
        check("pop is rejected again after all reviews are removed",rejectedPopAfterDrain);
    }

    // Producer: สำเนาและสแตกกลับลำดับต้องไม่เปิดเผยต้นฉบับ
    private static void testProducer() {
        System.out.println("\n-- Copy / Reversed movie reviews --");

        BoundedStack emptyMovieReviews = new BoundedStack(3);
        BoundedStack emptyCopy = emptyMovieReviews.copy();
        BoundedStack emptyReversed = emptyMovieReviews.reversed();
        check("copy and reversed of an empty stack are empty", emptyCopy.isEmpty() && emptyReversed.isEmpty());
        check("copy and reversed preserve capacity", emptyCopy.capacity() == 3 && emptyReversed.capacity() == 3);

        BoundedStack movieReviews = new BoundedStack(3);
        movieReviews.push("Interstellar", "ชวนคิด");
        movieReviews.push("The Dark Knight", "เข้มข้น");
        movieReviews.push("Spider-Man", "สนุกมาก");

        BoundedStack Copystack = movieReviews.copy();
        check("copy preserves review count and latest review",Copystack.size() == movieReviews.size());
        check("copy latest review",Copystack.peek().equals(movieReviews.peek()));
        Copystack.pop();
        check("popping from the copy does not change the original",Copystack.size() == 2 && movieReviews.size() == 3 && movieReviews.peek().getMovieTitle().equals("Spider-Man"));

        BoundedStack reversedReviews = movieReviews.reversed();
        check("reversed puts the original bottom review on top",sameReview(reversedReviews.pop(), "Interstellar", "ชวนคิด"));
        check("reversed preserves the remaining opposite order",sameReview(reversedReviews.pop(), "The Dark Knight", "เข้มข้น") && sameReview(reversedReviews.pop(), "Spider-Man", "สนุกมาก"));
        check("reversed does not mutate the original",movieReviews.size() == 3 && movieReviews.peek().getMovieTitle().equals("Spider-Man"));
    }

    // ข้อมูลโดเมน: ชื่อหนังและข้อความรีวิวต้องมีเนื้อหาจริง
    private static void testInvalidMovieReviewData() {
        System.out.println("\n-- Invalid movie-review data --");

        BoundedStack movieReviews = new BoundedStack(5);
        check("null movie title is rejected",pushThrowsIllegalArgument(movieReviews, null, "รีวิว"));
        check("empty movie title is rejected",pushThrowsIllegalArgument(movieReviews, "", "รีวิว"));
        check("null review text is rejected",pushThrowsIllegalArgument(movieReviews, "Interstellar", null));
        check("empty review text is rejected",pushThrowsIllegalArgument(movieReviews, "Interstellar", ""));
        check("invalid reviews never enter the stack", movieReviews.isEmpty());
    }

    private static boolean pushThrowsIllegalArgument(
            BoundedStack stack,
            String movieTitle,
            String reviewText) {
        try {
            stack.push(movieTitle, reviewText);
            return false;
        } catch (IllegalArgumentException exception) {
            return true;
        }
    }
}