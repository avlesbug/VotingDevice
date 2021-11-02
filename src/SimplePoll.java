public class SimplePoll {
    private Long id;
    private String name;
    private String question;
    private Long pollUserId;
    private boolean isPrivate;
    private Answer opt1;
    private Answer opt2;

    public SimplePoll(String name, String question,boolean isPrivate, Long pollUserId, Answer opt1, Answer opt2){

        this.name = name;
        this.question = question;
        this.isPrivate = isPrivate;
        this.pollUserId = pollUserId;
        this.opt1 = opt1;
        this.opt2 = opt2;

    }

    public String getQuestion(){ return question; }

    public Answer getOpt1() { return opt1; }

    public Answer getOpt2() { return opt2; }
}
