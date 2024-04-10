import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class Sapling extends Plant{
    class Branch implements Comparable<Branch>
    {
        boolean right;
        boolean finished = false;

        int growChance = 25;
        int length = 0;


        Queue<Point> unresolvedPoint = new PriorityQueue<Point>();

        Random rand = new Random();

        public Branch(boolean right, Point init, int growChance){
            this.right = right;
            this.growChance = Math.max(1, growChance);
            unresolvedPoint.add(init);
        }

        public void Grow(int horizontalChance){
            // -> Check grow chance
            if(rand.nextInt(growChance) == 0 && length > 3){
                finished = true;
                return;
            }

            // -> Reduce grow chance
            growChance = Math.max(1, growChance - 1);
            length++;

            // -> Check horizontal chance
            int x = 0;
            int y = 1;
            int rng = rand.nextInt(horizontalChance);
            if(rng < 3){
                if(right)
                    x = 1;
                else
                    x = -1;

                if(rng == 0)
                    y = 0;
            }

            // -> Place wood
            Point pulled = unresolvedPoint.remove();
            Program.gm.PlaceTile(pulled.X + x, pulled.Y, new Wood());
            unresolvedPoint.add(new Point(pulled.X + x, pulled.Y - y));
        }

        @Override
        public int compareTo(Branch o) {
            return 0;
        }
    }

    Queue<Point> unresolvedPoint = new PriorityQueue<Point>();
    Queue<Branch> unresolvedBranch = new PriorityQueue<Branch>();
    Branch currentBranch;
    boolean initSet = false;

    boolean branching = false;

    int growChance = 20;
    int growCount = 0;

    int length = 0;

    int midBranchChance = 5;
    boolean mbDir = false;

    Point growClamp = new Point(15, 30);
    Random rand = new Random();

    public Sapling (){
        tileName = "Sapling";
        tileColor = new Color(105, 86, 56);

        hasGravity = true;

        passThrough = new String[]{"Grass"};
    }

    @Override
    public void OnGrow() {
        // -> Check if game manager is null
        if(Program.gm == null)
            return;

        // -> Check if the sapling is stationary
        if(!stationary)
            return;

        // -> Set init point
        if(!initSet){
            unresolvedPoint.add(new Point(tilePoint.X, tilePoint.Y - 1));
            initSet = true;
        }

        // -> Start branching
        if(branching){
            // -> Branch tree
            if(!currentBranch.finished){
                currentBranch.Grow(rand.nextInt(10) + 1);
                return;
            }
            else if (unresolvedBranch.size() != 0){
                currentBranch = unresolvedBranch.remove();
                return;
            }

            canGrow = false;
            return;
        }

        // -> Increment grow count
        growCount++;

        // -> Check if the tree is done growing
        if (rand.nextInt(growChance) == 0 && growCount > growClamp.X) {
            branching = true;

            // -> Split into branches
            Point pulled = unresolvedPoint.remove();
            unresolvedBranch.add(new Branch(true, pulled, 15));
            unresolvedBranch.add(new Branch(false, pulled, 15));

            currentBranch = unresolvedBranch.remove();

            return;
        }

        // -> Grow trunk until split
        if(growCount < growClamp.Y) {
            Point pulledPoint = unresolvedPoint.remove();
            Program.gm.PlaceTile(pulledPoint.X, pulledPoint.Y, new Wood());
            length++;

            // -> Place new point
            unresolvedPoint.add(new Point(pulledPoint.X, pulledPoint.Y - 1));

            // -> Set chance for mid-branch
            if(rand.nextInt(midBranchChance) == 0 && length > 10) {
                unresolvedBranch.add(new Branch(mbDir, pulledPoint, 35 - length));
                mbDir = !mbDir;
            }
        }
    }
}
