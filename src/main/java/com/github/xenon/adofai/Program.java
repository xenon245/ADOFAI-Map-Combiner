package com.github.xenon.adofai;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import io.luxus.api.adofai.MapData;
import io.luxus.api.adofai.TileData;
import io.luxus.api.adofai.type.EventType;
import org.json.simple.parser.ParseException;

public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            Program.program(scanner);
            System.out.println("계속하시려면 엔터키를 눌러주세요.");
            System.in.read();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void program(Scanner scanner) {
        System.out.println("A Dance of Fire and Ice 맵 결합기");
        System.out.println("ver 1.0");
        System.out.println("개발자: xenon2452");
        System.out.println("Github: https://github.com/xenon2452");
        System.out.println();

        String maploc = null;
        MapData mapData = null;
        List<TileData> tileDataList = null;

        System.out.println("*.adofai 파일의 경로를 입력해야 합니다.*");
        System.out.print("(1번째 맵).adofai파일 : ");
        maploc = scanner.nextLine().trim();
        if(!maploc.endsWith(".adofai")) return;
        File file = new File(maploc);
        if(!file.exists()) {
            System.out.println("E> 파일이 존재하지 않습니다!");
            return;
        }

        try {
            mapData = new MapData();
            mapData.load(maploc);
            tileDataList = mapData.getTileDataList();
        } catch (Throwable t) {
            System.out.println("E> 오류 발생(" + maploc + ")");
            t.printStackTrace();
            return;
        }

        String maploc2 = null;
        MapData mapData2 = null;
        List<TileData> tileDataList2 = null;
        System.out.println("1번째 맵 로드 완료!");
        System.out.println("*.adofai 파일의 경로를 입력해야 합니다.*");
        System.out.print("(2번째 맵).adofai파일 : ");
        maploc2 = scanner.nextLine().trim();
        if(!maploc2.endsWith(".adofai")) return;
        File file2 = new File(maploc2);
        if(!file2.exists()) {
            System.out.println("E> 파일이 존재하지 않습니다.");
            return;
        }

        try {
            mapData2 = new MapData();
            mapData2.load(maploc2);
            tileDataList2 = mapData2.getTileDataList();
        } catch (Throwable t) {
            System.out.println("E> 오류 발생(" + maploc2 + ")");
            t.printStackTrace();
            return;
        }

        List<TileData> finalTileDataList = tileDataList;
        tileDataList2.remove(0);
        tileDataList2.forEach(tileData -> {
            tileData.floor += finalTileDataList.size();
        });
        tileDataList.addAll(tileDataList2);
        MapData newMap = new MapData(mapData.getSetting(), tileDataList);
        String outPath = maploc.replace(".adofai", "");
        try {
            newMap.save(outPath + "Combined.adofai");
        } catch (Throwable t) {
            System.out.println("E> 오류 발생(" + outPath + ")");
            t.printStackTrace();
        }
        System.out.println("Map Combined");
    }
}
