package ru.BouH_.world.automata;

public abstract class Automata {
    protected final int size;
    protected Structures[][] matrix;

    public Automata(int size) {
        this.size = size;
        this.matrix = new Structures[size][size];
    }

    public abstract void generate();

    public final int countStructures(Structures structures) {
        int total = 0;
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++) {
                if (this.getStructure(i, j) == structures) {
                    total += 1;
                }
            }
        }
        return total;
    }

    public final int checkNeighborWeight(Structures structures, int x, int y) {
        int weight = 0;
        if (this.checkBoundX(x - 1) && this.getStructure(x - 1, y) == structures) {
            weight += 1;
        }
        if (this.checkBoundX(x + 1) && this.getStructure(x + 1, y) == structures) {
            weight += 1;
        }
        if (this.checkBoundY(y - 1) && this.getStructure(x, y - 1) == structures) {
            weight += 1;
        }
        if (this.checkBoundY(y + 1) && this.getStructure(x, y + 1) == structures) {
            weight += 1;
        }
        return weight;
    }

    public final int checkWeight(Structures structures, int x, int y) {
        return this.checkWeightDiagonal(structures, x, y) + this.checkNeighborWeight(structures, x, y);
    }

    public final int checkLongWeight(Structures structures, int x, int y) {
        int weight = 0;
        if (this.checkBoundX(x - 1) && this.getStructure(x - 1, y) == structures) {
            weight += 1;
        }
        if (this.checkBoundX(x - 2) && this.getStructure(x - 2, y) == structures) {
            weight += 1;
        }
        if (this.checkBoundX(x + 1) && this.getStructure(x + 1, y) == structures) {
            weight += 1;
        }
        if (this.checkBoundX(x + 2) && this.getStructure(x + 2, y) == structures) {
            weight += 1;
        }
        if (this.checkBoundY(y - 1) && this.getStructure(x, y - 1) == structures) {
            weight += 1;
        }
        if (this.checkBoundY(y - 2) && this.getStructure(x, y - 2) == structures) {
            weight += 1;
        }
        if (this.checkBoundY(y + 1) && this.getStructure(x, y + 1) == structures) {
            weight += 1;
        }
        if (this.checkBoundY(y + 2) && this.getStructure(x, y + 2) == structures) {
            weight += 1;
        }
        return weight;
    }

    public final int checkWeightDiagonal(Structures structures, int x, int y) {
        int weight = 0;
        if (this.checkBoundY(y - 1) && this.checkBoundX(x - 1) && this.getStructure(x - 1, y - 1) == structures) {
            weight += 1;
        }
        if (this.checkBoundY(y + 1) && this.checkBoundX(x - 1) && this.getStructure(x - 1, y + 1) == structures) {
            weight += 1;
        }
        if (this.checkBoundY(y - 1) && this.checkBoundX(x + 1) && this.getStructure(x + 1, y - 1) == structures) {
            weight += 1;
        }
        if (this.checkBoundY(y + 1) && this.checkBoundX(x + 1) && this.getStructure(x + 1, y + 1) == structures) {
            weight += 1;
        }
        return weight;
    }

    public final boolean checkBoundX(int x) {
        return x >= 0 && x < this.getSize();
    }

    public final boolean checkBoundY(int y) {
        return y >= 0 && y < this.getSize();
    }

    public final int getSize() {
        return this.size;
    }

    public final boolean checkStructure(int x, int y, Structures structures) {
        return this.checkBoundX(x) && this.checkBoundY(y) && this.getStructure(x, y) == structures;
    }

    public final Structures getStructure(int x, int y) {
        return this.matrix[x][y];
    }
}