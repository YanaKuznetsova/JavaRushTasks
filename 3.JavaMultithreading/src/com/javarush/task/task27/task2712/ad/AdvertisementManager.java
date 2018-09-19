package com.javarush.task.task27.task2712.ad;



import com.javarush.task.task27.task2712.ConsoleHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class AdvertisementManager {

    private int timeSeconds;
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() {
        List<Advertisement> availableVideos = storage.list();
        if (availableVideos.isEmpty()) {
            throw new NoVideoAvailableException();
        } else {
            List<List<Advertisement>> suitableAds = Permutations.selectOnlySuitableAds(availableVideos, timeSeconds);
            System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
            Collections.sort(suitableAds, new Comparator<List<Advertisement>>() {
                @Override
                public int compare(List<Advertisement> list1, List<Advertisement> list2) {
                    int summaryIncome1 = 0;
                    int summaryDuration1 = 0;
                    int summaryIncome2 = 0;
                    int summaryDuration2 = 0;
                    for (Advertisement ad : list1){
                        summaryIncome1 += ad.getAmountPerOneDisplaying();
                        summaryDuration1 += ad.getDuration();
                    }
                    for (Advertisement ad : list2){
                        summaryIncome2 += ad.getAmountPerOneDisplaying();
                        summaryDuration2 = ad.getDuration();
                    }
                    if (summaryIncome1 != summaryIncome2) {
                        return summaryIncome2 - summaryIncome1;
                    } else if (summaryDuration1 != summaryDuration2) {
                        return summaryDuration2 - summaryDuration1;
                    }
                    return list1.size() - list2.size();
                }
            });
            Collections.sort(suitableAds.get(0), new Comparator<Advertisement>() {
                @Override
                public int compare(Advertisement ad1, Advertisement ad2) {
                    if (ad1.getAmountPerOneDisplaying() != ad2.getAmountPerOneDisplaying()) {
                        return (int) (ad2.getAmountPerOneDisplaying() - ad1.getAmountPerOneDisplaying());
                    }
                    return (int) (ad1.getAmountPerOneDisplaying()*1000/ad1.getDuration()
                                - ad2.getAmountPerOneDisplaying()*1000/ad2.getDuration());
                }
            });
            for (Advertisement advertisement: suitableAds.get(0)) {
                ConsoleHelper.writeMessage(String.format("%s is displaying... %d, %d",
                        advertisement.getName(), advertisement.getAmountPerOneDisplaying(),
                        advertisement.getAmountPerOneDisplaying()*1000/advertisement.getDuration()));
                advertisement.revalidate();
            }
        }
    }

    public static class Permutations {

        private static int[] arrayWithIndexes;
        private static List<List<Advertisement>> listOfLists;
        private static List<Advertisement> initList;
        private static int range;

        static List<List<Advertisement>> selectOnlySuitableAds(List<Advertisement> availableVideos, int orderDuration){
            getListOfAdsWithPositiveHits(availableVideos);
            getAllPossibleAdPermutations();

            for (List<Advertisement> list : listOfLists) {
                int summaryDuration = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (summaryDuration + list.get(i).getDuration() > orderDuration) {
                        while (i + 1 <= list.size()) {
                            list.remove(i);
                        }
                    } else {
                        summaryDuration += list.get(i).getDuration();
                    }
                }
            }
            return listOfLists;
        }

        private static void getListOfAdsWithPositiveHits(List<Advertisement> availableVideos) {
            initList = new ArrayList<>();
            for (Advertisement advertisement: availableVideos) {
                if (advertisement.getHits() > 0) {
                    initList.add(advertisement);
                }
            }
        }

        private static void getAllPossibleAdPermutations() {
            listOfLists = new ArrayList<>();
            range = initList.size();
            arrayWithIndexes= IntStream.rangeClosed(0, range - 1).toArray();
            permutation(0, range);
        }

        private static void permutation(int left, int right) {
            if (left != right) {
                for (int i = left; i < right; i++) {
                    int tmp = arrayWithIndexes[left];
                    arrayWithIndexes[left] = arrayWithIndexes[i];
                    arrayWithIndexes[i] = tmp;
                    permutation(left + 1, right);
                    tmp = arrayWithIndexes[left];
                    arrayWithIndexes[left] = arrayWithIndexes[i];
                    arrayWithIndexes[i] = tmp;
                }
            } else {
                List<Advertisement> newList = new ArrayList<>();
                for (int i = 0; i < range; i++) {
                    newList.add(initList.get(arrayWithIndexes[i]));
                }
                listOfLists.add(newList);
            }
        }

//        private static void tempPrint(List<Advertisement> list) {
//            System.out.println("==========================================");
//            for (Advertisement ad: list) {
//                System.out.println(ad.getName());
//            }
//        }
    }


//    public void processVideos() throws NoVideoAvailableException {
//
//        List<Advertisement> videos = storage.list();
//        if (storage.list().isEmpty())
//            throw new NoVideoAvailableException();
//
//        // ищем список видео для показа согласно критериям
//        List<Advertisement> bestAds = new VideoHelper().findAllYouNeed();
//
//        // сортируем полученный список
//        Collections.sort(bestAds, new Comparator<Advertisement>() {
//            @Override
//            public int compare(Advertisement video1, Advertisement video2) {
//                long dif = video2.getAmountPerOneDisplaying() - video1.getAmountPerOneDisplaying();
//                if (dif == 0) dif = video2.getDuration() - video1.getDuration();
//                return (int) dif;
//            }
//        });
//        long amount = 0;
//        int totalDuration = 0;
//        for (Advertisement ad : bestAds) {
//            totalDuration += ad.getDuration();
//            amount += ad.getAmountPerOneDisplaying();
//        }
//        //StatisticManager.getInstance().register(new VideoSelectedEventDataRow(bestAds, amount, totalDuration));
//
//
//        // выводим список
//        for (Advertisement ad : bestAds) {
//            ConsoleHelper.writeMessage(ad.getName() + " is displaying... " +
//                    ad.getAmountPerOneDisplaying() + ", " +
//                    1000 * ad.getAmountPerOneDisplaying() / ad.getDuration());
//            ad.revalidate();
//        }
//    }
//
//    private class VideoHelper {
//        private int bestPrice = 0;
//        private int maxTime = 0;
//        private int numberOfClips = 0;
//        private List<Advertisement> bestAds = new ArrayList<>();
//        private List<Advertisement> candidates = new ArrayList<>();
//
//        List<Advertisement> findAllYouNeed() {
//            // отбор кандидатов
//            for (Advertisement ad : storage.list()) {
//                if (ad.getDuration() <= timeSeconds && ad.getHits() > 0)
//                    candidates.add(ad);
//            }
//            if (candidates.isEmpty()) {
//                throw new NoVideoAvailableException();
//            } else findBestAds(new BinaryPattern(candidates.size()));
//            return bestAds;
//        }
//
//        // рекурсивная функция формирования списка для показа
//        void findBestAds(BinaryPattern pattern) {
//            while (true) {
//                checkAds(pattern.getPattern());
//                if (!pattern.full()) pattern.increment();
//                else break;
//                findBestAds(pattern);
//            }
//        }
//
//        // проверка очередного набора видеоклипов
//        private void checkAds(int[] pattern) {
//            int price = 0;
//            int time = 0;
//            List<Advertisement> list = new ArrayList<>();
//            for (int i = 0; i < candidates.size(); i++) {
//                price += candidates.get(i).getAmountPerOneDisplaying() * pattern[i];
//                time += candidates.get(i).getDuration() * pattern[i];
//                if (pattern[i] == 1) list.add(candidates.get(i));
//            }
//            if (time > timeSeconds) return;
//            if (!(price > bestPrice)) {
//                if (!(price == bestPrice && time > maxTime)) {
//                    if (!(price == bestPrice && time == maxTime && list.size() < numberOfClips)) {
//                        return;
//                    }
//                }
//            }
//            bestAds = list;
//            bestPrice = price;
//            maxTime = time;
//            numberOfClips = list.size();
//        }
//
//        // формирование двоичных масок для сбора списка видеоклипов
//        private class BinaryPattern {
//            private int length;
//            private int count;
//
//            BinaryPattern(int size) {
//                this.length = size;
//                this.count = 0;
//            }
//
//            int[] getPattern() {
//                String regString = Integer.toBinaryString(count);
//                int dif = length - regString.length();
//                int[] pattern = new int[length];
//                for (int j = dif; j < pattern.length; j++) {
//                    if (regString.charAt(j - dif) == '1') pattern[j] = 1;
//                    else pattern[j] = 0;
//                }
//                return pattern;
//            }
//
//            void increment() {
//                count++;
//            }
//
//            boolean full() {
//                return count == (int) Math.pow(2, length) - 1;
//            }
//        }
//    }

}
