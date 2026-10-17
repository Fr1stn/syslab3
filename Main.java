import java.util.Random;

class EggOrChicken {
    private static volatile String lastWord = "";

    static class Speaker extends Thread {
        private final String word;
        private final int times;
        private final Random random = new Random();

        Speaker(String word, int times) {
            this.word = word;
            this.times = times;
        }

        @Override
        public void run() {
            for (int i = 0; i < times; i++) {
                System.out.println(word);
                lastWord = word;
                try {
                    Thread.sleep(random.nextInt(400) + 100);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    void main() {
        Speaker chicken = new Speaker("Курица", 5);
        Speaker egg = new Speaker("Яйцо", 5);

        System.out.println("Спор начался!");
        chicken.start();
        egg.start();
        while (chicken.isAlive() || egg.isAlive()) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("... спор продолжается (Курица жива: " + chicken.isAlive()
                    + ", Яйцо живо: " + egg.isAlive() + ")");
        }

        try {
            chicken.join();
            egg.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Спор окончен!");
        if (lastWord.equals("Яйцо")) {
            System.out.println("Результат: первым появилось ЯЙЦО (последнее слово за ним).");
        } else {
            System.out.println("Результат: первой появилась КУРИЦА (последнее слово за ней).");
        }
    }
}