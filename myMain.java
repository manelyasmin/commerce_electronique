package sample;

public class myMain {
    public static void main(String[] args) throws Exception {

        double sumGain = 0;
        double sumTime = 0;
        int numberOfAttempts = 10;
int i=1;
        for ( ; i <= numberOfAttempts; i++) {
            FormuleWDP f = new FormuleWDP("instance/in60"+i);
            GA genetic = new GA(f);


        long currentTime = System.currentTimeMillis();
        SolutionWDP s = genetic.memeticAlgo(f, 100, i*10, 0.1);
        long exeTime = System.currentTimeMillis() - currentTime;
        double t = ((double) exeTime / (1000));
        sumGain = s.getGain();
        sumTime = t;

        System.out.println("[file , i , j , k ] "+"[" + i  +"]"  +"sumgain" + sumGain + "\t" + "sumTime" + sumTime);

        }
        }
}
