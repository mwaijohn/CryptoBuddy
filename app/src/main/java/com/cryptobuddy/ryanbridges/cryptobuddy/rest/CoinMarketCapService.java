package com.cryptobuddy.ryanbridges.cryptobuddy.rest;

import android.content.Context;
import android.util.Log;

import com.cryptobuddy.ryanbridges.cryptobuddy.currencydetails.chartandtable.GraphFragment;
import com.cryptobuddy.ryanbridges.cryptobuddy.models.rest.CMCChartData;
import com.cryptobuddy.ryanbridges.cryptobuddy.models.rest.CMCCoin;
import com.grizzly.rest.GenericRestCall;
import com.grizzly.rest.Model.afterTaskCompletion;
import com.grizzly.rest.Model.afterTaskFailure;

import org.springframework.http.HttpMethod;

/**
 * Created by Ryan on 1/16/2018.
 */

public class CoinMarketCapService {

    public static final String PRO_CMC_ALL_COINS_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?sort=market_cap&CMC_PRO_API_KEY=9de23807-460a-4991-9389-aaea74b440b8";
    public static final String COIN_MARKETCAP_ALL_COINS_URL = "https://api.coinmarketcap.com/v1/ticker/?limit=0";
    public static final String COIN_MARKETCAP_CHART_URL_ALL_DATA = "https://graphs2.coinmarketcap.com/currencies/%s/";
    public static final String COIN_MARKETCAP_CHART_URL_WINDOW = "https://graphs2.coinmarketcap.com/currencies/%s/%s/%s/";

    public static void getAllCoins(Context context, afterTaskCompletion<CMCCoin> taskCompletion, afterTaskFailure failure, boolean async) {
        new GenericRestCall<>(Void.class, CMCCoin.class, String.class)
                .setUrl(PRO_CMC_ALL_COINS_URL)
                .setContext(context.getApplicationContext())
                .isCacheEnabled(true)
                .setCacheTime(300000L)
                .setMethodToCall(HttpMethod.GET)
                .setTaskCompletion(taskCompletion)
                .setTaskFailure(failure)
                .setAutomaticCacheRefresh(true).execute(async);
    }

    public static void getCMCChartData(Context context, String coinID, afterTaskCompletion<CMCChartData> taskCompletion, afterTaskFailure failure, boolean async) {
        Log.d("I", "URL: " + GraphFragment.CURRENT_CHART_URL);
        new GenericRestCall<>(Void.class, CMCChartData.class, String.class)
                .setUrl(String.format(GraphFragment.CURRENT_CHART_URL, coinID))
                .setContext(context.getApplicationContext())
                .isCacheEnabled(true)
                .setCacheTime(60000L)
                .setMethodToCall(HttpMethod.GET)
                .setTaskCompletion(taskCompletion)
                .setTaskFailure(failure)
                .setAutomaticCacheRefresh(false).execute(async);
    }
}
