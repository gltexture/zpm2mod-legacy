package ru.BouH_.world.automata;

import ru.BouH_.Main;

public class MilitaryGenAutomata extends Automata {
    public MilitaryGenAutomata(int size) {
        super(size);
    }

    public void generate() {
        boolean flag = false;
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++) {
                this.matrix[i][j] = Main.rand.nextBoolean() ? Structures.WHITE : Structures.ROAD_RAW;
            }
        }

        int iterations = 0;
        while (iterations++ <= 4) {
            this.generateRoads();
        }
        loop1:
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++) {
                if (this.getStructure(i, j) == Structures.ROAD_RAW) {
                    this.matrix[i][j] = Structures.ROAD_VISITED;
                    break loop1;
                }
            }
        }
        this.fixMistakes();
        this.generateSpecial();
        this.generateGreen();
    }

    private void generateRoads() {
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++) {
                if (this.getStructure(i, j) == Structures.ROAD_RAW) {
                    if (this.checkNeighborWeight(Structures.WHITE, i, j) < 2) {
                        this.matrix[i][j] = Structures.WHITE;
                    }
                }
                if (this.getStructure(i, j) == Structures.WHITE) {
                    if (this.checkNeighborWeight(Structures.WHITE, i, j) >= 3) {
                        this.matrix[i][j] = Structures.ROAD_RAW;
                    }
                }
                if (this.checkWeightDiagonal(Structures.WHITE, i, j) >= 2 || this.checkLongWeight(Structures.ROAD_RAW, i, j) <= 2) {
                    this.matrix[i][j] = Structures.ROAD_RAW;
                }
                if (this.checkWeight(Structures.ROAD_RAW, i, j) >= 5) {
                    this.matrix[i][j] = Structures.WHITE;
                }
            }
        }
    }

    private void fixMistakes() {
        boolean flag = false;
        while (!flag) {
            flag = true;
            for (int i = 0; i < this.getSize(); i++) {
                for (int j = 0; j < this.getSize(); j++) {
                    if (this.getStructure(i, j) == Structures.ROAD_RAW) {
                        if (this.checkNeighborWeight(Structures.ROAD_VISITED, i, j) != 0) {
                            this.matrix[i][j] = Structures.ROAD_VISITED;
                            flag = false;
                        }
                    }
                    if (this.getStructure(i, j) == Structures.ROAD_VISITED) {
                        if (this.checkBoundX(i + 1) && this.checkBoundY(j + 1) && this.getStructure(i + 1, j) == Structures.ROAD_VISITED && this.getStructure(i, j + 1) == Structures.ROAD_VISITED && this.getStructure(i + 1, j + 1) == Structures.ROAD_VISITED) {
                            if (this.checkNeighborWeight(Structures.ROAD_VISITED, i + 1, j) == 2) {
                                this.matrix[i + 1][j] = Structures.WHITE;
                                flag = false;
                            } else if (this.checkNeighborWeight(Structures.ROAD_VISITED, i, j + 1) == 2) {
                                this.matrix[i][j + 1] = Structures.WHITE;
                                flag = false;
                            } else if (this.checkNeighborWeight(Structures.ROAD_VISITED, i + 1, j + 1) == 2) {
                                this.matrix[i + 1][j + 1] = Structures.WHITE;
                                flag = false;
                            } else if (this.checkNeighborWeight(Structures.ROAD_VISITED, i, j) == 2) {
                                this.matrix[i][j] = Structures.WHITE;
                                flag = false;
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++) {
                if (this.getStructure(i, j) == Structures.WHITE) {
                    if (this.checkNeighborWeight(Structures.ROAD_VISITED, i, j) != 0 && this.checkNeighborWeight(Structures.ROAD_RAW, i, j) != 0) {
                        this.matrix[i][j] = Structures.ROAD_RAW;
                        this.fixMistakes();
                        return;
                    }
                }
            }
        }
    }

    private void generateSpecial() {
        for (int i = 1; i < this.getSize() - 1; i++) {
            for (int j = 1; j < this.getSize() - 1; j++) {
                if (this.getStructure(i, j) == Structures.WHITE && this.checkNeighborWeight(Structures.WHITE, i, j) == 2 && this.checkLongWeight(Structures.GRAY, i, j) == 0) {
                    this.matrix[i][j] = Structures.GRAY;
                }
            }
        }
    }

    public void generateGreen() {
        for (int i = 1; i < this.getSize() - 1; i++) {
            for (int j = 1; j < this.getSize() - 1; j++) {
                if (this.getStructure(i, j) == Structures.WHITE && this.checkWeight(Structures.GREEN, i, j) == 0 && this.checkWeight(Structures.GRAY, i, j) == 0) {
                    this.matrix[i][j] = Structures.GREEN;
                }
            }
        }
    }
}
