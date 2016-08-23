import java.awt.Graphics2D;
import java.awt.Image;
import java.io.*;

import org.json.JSONArray;
import org.json.JSONObject;

/*
 * Created on 18/05/2015
 *
 */

/**
 * @author Dennis Kerr Coelho
 * 
 */
public class TileMapJSON {

	public Image TileSet = null;

	int MapX;
	int MapY;
	int NTileX, NTileY;
	int Largura = 60;
	int Altura = 50;
	
	int tileW = 16;
	int tileH = 16;
	
	int TilePLinhaTileset;

	int[][] mapa;
	int[][] mapa2;

	public TileMapJSON(Image tileset, int tilestelaX, int tilestelaY) {
		NTileX = tilestelaX;
		NTileY = tilestelaY;
		TileSet = tileset;
		MapX = 0;
		MapY = 0;
		TilePLinhaTileset = TileSet.getWidth(null) >> 4;
	}

	public void AbreMapa(String nomemapa) {

		InputStream In = getClass().getResourceAsStream(nomemapa);
		InputStreamReader inr = new InputStreamReader(In);
		BufferedReader br = new BufferedReader(inr);
		String jsonfinal = "";
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			jsonfinal = sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		JSONObject jobj = new JSONObject(jsonfinal);
		
		Largura = jobj.getInt("width");
		Altura = jobj.getInt("height");
		tileW = jobj.getInt("tilewidth");
		tileH = jobj.getInt("tileheight");
		
		JSONArray layers = jobj.getJSONArray("layers");
		JSONArray layar1 = layers.getJSONObject(0).getJSONArray("data");
		JSONArray layar2 = layers.getJSONObject(1).getJSONArray("data");
		
		TilePLinhaTileset = TileSet.getWidth(null)/tileW;
		
		mapa = new int[Altura][Largura];
		
		System.out.println(" TilePLinhaTileset "+TilePLinhaTileset);
		for(int j = 0; j < Altura;j++){
			for(int i = 0; i < Largura;i++){
				int index = j*Largura+i;
				//System.out.println("index "+index);
				mapa[j][i] = layar1.getInt(index)-1;
				if(mapa[j][i]<0){
					mapa[j][i] = 0;
				}
			}
		}
		mapa2 = new int[Altura][Largura];
		for(int j = 0; j < Altura;j++){
			for(int i = 0; i < Largura;i++){
				int index = j*Largura+i;
				mapa2[j][i] = layar2.getInt(index)-1;
				if(mapa2[j][i]<0){
					mapa2[j][i] = 0;
				}	
			}
		}
	}

	public void DesenhaSe(Graphics2D dbg) {
		int offx = MapX % tileW;
		int offy = MapY % tileH;
		//System.out.println(" "+offx+" "+offy+" "+MapX+" "+MapY);
		int somax, somay;
		if (offx > 0) {
			somax = 1;
		} else {
			somax = 0;
		}
		if (offy > 0) {
			somay = 1;
		} else {
			somay = 0;
		}
		for (int j = 0; j < NTileY + somay; j++) {
			for (int i = 0; i < NTileX + somax; i++) {

				int tilex = (mapa[j + (MapY /tileH)][i + (MapX /tileH)] % TilePLinhaTileset) * tileW;
				int tiley = (mapa[j + (MapY /tileH)][i + (MapX /tileH)] / TilePLinhaTileset) * tileH;
				int id = i *  tileW;
				int jd = j *  tileH;
				dbg.drawImage(TileSet, id - offx, jd - offy,id + tileW - offx, jd + tileH - offy, tilex,tiley, tilex + tileW, tiley + tileH, null);
			}
		}
		for (int j = 0; j < NTileY + somay; j++) {
			for (int i = 0; i < NTileX + somax; i++) {

				int tilex = (mapa2[j + (MapY /tileH)][i + (MapX /tileH)] % TilePLinhaTileset) * tileW;
				int tiley = (mapa2[j + (MapY /tileH)][i + (MapX /tileH)] / TilePLinhaTileset) * tileH;
				int id = i *  tileW;
				int jd = j *  tileH;
				dbg.drawImage(TileSet, id - offx, jd - offy,id + tileW - offx, jd + tileH - offy, tilex,tiley, tilex + tileW, tiley + tileH, null);
			}
		}
	}

	public void Posiciona(int x, int y) {
		int X = x / tileW;
		int Y = y / tileH;

		if (X < 0) {
			MapX = 0;
		} else if (X >= (Largura - NTileX)) {
			MapX = ((Largura - NTileX)) *tileW;
		} else {
			if(x >0){
				MapX = x;
			}else{
				MapX = 0;
			}
		}

		if (Y < 0) {
			MapY = 0;
		} else if (Y >= (Altura - NTileY)) {
			MapY = ((Altura - NTileY)) *tileH;
		} else {
			if(y >0){
				MapY = y;
			}else{
				MapY = 0;
			}
		}

	}
}
