public class Player {
    private final String name;
    private int position;          
    private int consecutiveSixes;  
    private boolean hasWon;

    public Player(String name) {
        this.name = name;
        this.position = 0;
        this.consecutiveSixes = 0;
        this.hasWon = false;
    }

    public String getName()            { return name;            }
    public int    getPosition()        { return position;        }
    public int    getConsecutiveSixes(){ return consecutiveSixes; }
    public boolean hasWon()            { return hasWon;          }

    public void setPosition(int position)         { this.position = position;        }
    public void setConsecutiveSixes(int val)      { this.consecutiveSixes = val;     }
    public void setHasWon(boolean hasWon)         { this.hasWon = hasWon;            }

    public void incrementConsecutiveSixes()       { this.consecutiveSixes++;         }
    public void resetConsecutiveSixes()           { this.consecutiveSixes = 0;       }

    @Override
    public String toString() {
        return name + "(pos=" + position + ")";
    }
}
