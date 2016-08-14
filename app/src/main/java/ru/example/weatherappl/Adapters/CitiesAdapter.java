package ru.example.weatherappl.Adapters;

        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Typeface;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.widget.CardView;
        import android.support.v7.widget.RecyclerView;
        import android.text.Html;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
        import android.widget.Toast;


        import io.realm.Realm;
        import ru.example.weatherappl.Helper.Prefs;
        import ru.example.weatherappl.Helper.RController;
        import ru.example.weatherappl.Model.LocationMapObject;
        import ru.example.weatherappl.Main.CityWeatherActivity;
        import ru.example.weatherappl.R;

public class CitiesAdapter extends RealmRecyclerViewAdapter<LocationMapObject> {

    final Context context;
    private Realm realm;

    public CitiesAdapter(Context context) {
        this.context = context;
    }
    Typeface weatherFont;

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView message;
        public TextView textName;
        public TextView degree;
        public TextView icon;


        public CardViewHolder(View itemView) {

            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card_cities);
            textName = (TextView) itemView.findViewById(R.id.text_city_name);
            message = (TextView) itemView.findViewById(R.id.temp_info);
            degree = (TextView) itemView.findViewById(R.id.degree);
            icon = (TextView) itemView.findViewById(R.id.weather_icon);

        }
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        weatherFont = Typeface.createFromAsset(context.getAssets(), "fonts/weather.ttf");

        CardViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        viewHolder = new CardViewHolder(view);
        return viewHolder;

           }




    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        realm = RController.getInstance().getRealm();
        final LocationMapObject cityWeather = getItem(position);
        final CardViewHolder vHolder = (CardViewHolder) holder;




        vHolder.textName.setText(cityWeather.getName());

        try {
            vHolder.message.setText(cityWeather.getWeather().get(0).getMain());
           vHolder.degree.setText(Math.round(Math.floor(Double.parseDouble(cityWeather.getMain().getTemp()))) + " \u2103");
            vHolder.icon.setText(Html.fromHtml(CityWeatherActivity.setWeatherIcon(cityWeather.getWeather().get(0).getMain())));
            vHolder.icon.setTypeface(weatherFont);
        } catch (Exception ex) {

        }

        vHolder.card.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete City?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String name = cityWeather.getName();

                                realm.beginTransaction();
                                cityWeather.deleteFromRealm();
                                realm.commitTransaction();

                                if (realm.where(LocationMapObject.class).findAll().size() == 0) {
                                    Prefs.with(context).setPreLoad(false);
                                }

                                notifyDataSetChanged();

                                Toast.makeText(context, name + " is removed from List", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

               AlertDialog dialog = builder.create();
                dialog.show();


                return false;
                        }
        });

        vHolder.card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, CityWeatherActivity.class);
                intent.putExtra("name", cityWeather.getName());
                context.startActivity(intent);



            }
        });
        }

    @Override
    public int getItemCount() {
        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;

    }


}
