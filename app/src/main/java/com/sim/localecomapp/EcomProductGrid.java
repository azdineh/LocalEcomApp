package com.sim.localecomapp;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.sim.localecomapp.network.ImageRequester;
import com.sim.localecomapp.network.ProductEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class EcomProductGrid extends Fragment implements  ProductCardRecyclerViewAdapter.OnItemClickListener{

    final String  TAG="Ecom";
    List<ProductEntry> products=new ArrayList<>();
    ProductEntry currentViewedProduct;
    //product elements subview
    TextView productTitle,productPrice,productDescription,totalTextView;
    NetworkImageView productImage;
    View productDestails_subview,productçgrid,aboutusSubview,cartSubview,addTocartButton,clearButton,orderAllButton;
    MaterialButton backButton,orderButton,buttonHome,buttonAboutUs,buttonCart,buttonContactUs;
    ImageRequester imageRequester=ImageRequester.getInstance();
    BackDropAnimator backDropAnimator;
    ListView cartList;
    RecyclerView recyclerView;
    ArrayAdapter adaptercart;
    ProductCardRecyclerViewAdapter adapter;

    MenuItem cart_count;
    Menu lmenu;

    View fragmentView;

    Cart cart=new Cart();

    Toolbar toolbar;

    NavigationIconClickListener navigationClickListener;

    private int shortAnimationDuration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Log.i(TAG, "handleOnBackPressed: ");
                getActivity().finish();
                System.exit(0);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");

    }



    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView =inflater.inflate(R.layout.ecom_product_grid, container, false);
        setUpToolbar(fragmentView);

        productTitle=fragmentView.findViewById(R.id.product_title);
        productPrice=fragmentView.findViewById(R.id.product_price);
        productDescription=fragmentView.findViewById(R.id.product_description);

        productImage=fragmentView.findViewById(R.id.product_image);

        productDestails_subview=fragmentView.findViewById(R.id.product_details);
        aboutusSubview=fragmentView.findViewById(R.id.aboutus_subview);
        cartSubview=fragmentView.findViewById(R.id.cart_subview);
        productçgrid=fragmentView.findViewById(R.id.recycler_view);
        addTocartButton=fragmentView.findViewById(R.id.add_to_card_button);

        cartList=fragmentView.findViewById(R.id.cart_listview);

        buttonHome=fragmentView.findViewById(R.id.button_home);
        buttonAboutUs=fragmentView.findViewById(R.id.button_aboutus);
        buttonCart=fragmentView.findViewById(R.id.button_cart);
        orderAllButton=fragmentView.findViewById(R.id.order_all_button);
        clearButton=fragmentView.findViewById(R.id.clear_button);
        totalTextView=fragmentView.findViewById(R.id.total_textview);

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cartSubview.setVisibility(View.GONE);
                aboutusSubview.setVisibility(View.GONE);

                crossfadeOut((LinearLayout) productDestails_subview);

                backDropAnimator=BackDropAnimator.getInstance(
                        getContext(),
                        view.findViewById(R.id.product_grid),
                        null,
                        getContext().getResources().getDrawable(R.drawable.ecom_icon_menu),
                        getContext().getResources().getDrawable(R.drawable.ecom_icon_menu_close)
                );
                backDropAnimator.moveBackDrop();


            }
        });

        buttonAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productDestails_subview.setVisibility(View.GONE);
                cartSubview.setVisibility(View.GONE);
                crossfade((LinearLayout) aboutusSubview);
                backDropAnimator=BackDropAnimator.getInstance(
                        getContext(),
                        view.findViewById(R.id.product_grid),
                        null,
                        getContext().getResources().getDrawable(R.drawable.ecom_icon_menu),
                        getContext().getResources().getDrawable(R.drawable.ecom_icon_menu_close)
                );
                backDropAnimator.moveBackDrop();
            }
        });

        buttonCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                adaptercart = new ArrayAdapter<ProductEntry>(getContext(),android.R.layout.simple_list_item_1,cart.getProducts());
                cartList.setAdapter(adaptercart);
                productDestails_subview.setVisibility(View.GONE);
                aboutusSubview.setVisibility(View.GONE);

                crossfade((LinearLayout) cartSubview);
                backDropAnimator=BackDropAnimator.getInstance(
                        getContext(),
                        view.findViewById(R.id.product_grid),
                        null,
                        getContext().getResources().getDrawable(R.drawable.ecom_icon_menu),
                        getContext().getResources().getDrawable(R.drawable.ecom_icon_menu_close)
                );
                backDropAnimator.moveBackDrop();

            }
        });



        addTocartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentViewedProduct!= null){

                    cart.addProduct(currentViewedProduct);
                    totalTextView.setText(""+cart.getTotal());
                    //cart_count.setTitle();
                    lmenu.getItem(1).setTitle(""+cart.getProducts().size());

                    ViewGroup.LayoutParams layoutParams=cartList.getLayoutParams();
                    layoutParams.height+=dpToPx(50);
                    cartList.setLayoutParams(layoutParams);
                }

            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.clear();
                totalTextView.setText("");
                lmenu.getItem(1).setTitle(""+cart.getProducts().size());
                cartList.setAdapter(adaptercart);

            }
        });

        orderAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number=view.getContext().getString(R.string.whatsapp_number);
                String message="Salam alicom, please I want :\n";

                float total=0;
                for (int i = 0; i < cart.getProducts().size() ; i++) {
                    ProductEntry p=cart.getProducts().get(i);
                    message+="*"+p.title+"*:"+ p.price+" DH\n";
                    total+=Float.parseFloat(p.price);

                }
                message+="*~Total :"+total+" DH~*";

                String url = "https://api.whatsapp.com/send?phone="+number+"&text="+message;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                Context context = view.getContext();
                context.startActivity(i);
            }
        });




        // Initially hide the content view.
        productDestails_subview.setVisibility(View.GONE);
        cartSubview.setVisibility(View.GONE);
        aboutusSubview.setVisibility(View.GONE);

        // Retrieve and cache the system's default "short" animation time.
        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        backButton=fragmentView.findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //productDestails_subview.setVisibility(View.GONE);
                //productçgrid.setVisibility(View.VISIBLE);
                crossfadeOut((LinearLayout) productDestails_subview);


            }
        });

        // Set cut corner background for API 23+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fragmentView.findViewById(R.id.product_grid).setBackgroundResource(R.drawable.ecom_product_grid_background_shape);
        }


        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        String url="https://ecommercebackendd.herokuapp.com/api/produits?start=1&count=10";
        StringRequest stringRequest=new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: " + response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray= jsonObject.getJSONArray("products");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject productObject = jsonArray.getJSONObject(i);
                        ProductEntry p=new ProductEntry(productObject.getString("nom"),"",productObject.getString("image_url"),
                                productObject.getString("prix"), productObject.getString("description"));
                        products.add(p);
                    }
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: "+ error.getMessage());
            }
        });
        requestQueue.add(stringRequest);


//        ProductEntry p1=new ProductEntry("One Step","","https://ma.jumia.is/unsafe/fit-in/680x680/filters:fill(white)/product/09/293263/1.jpg?8828","199","A nice product");
//        products.add(p1);
//        ProductEntry p2=new ProductEntry("Black mask","","https://www.hivis.co.uk/images/thumbnails/450/397/detailed/72/black_mask_fitted_38bf-kc.JPG","89","A nice mask ");
//        products.add(p2);
//        ProductEntry p3=new ProductEntry("Pack Collagen","","https://rukminim1.flixcart.com/image/416/416/jua4djk0/face-treatment/6/w/g/30-collagen-serum-snail-original-imafffw3avgnnbjt.jpeg?q=70","180","A nice pack collagen");
//        products.add(p3);
//        ProductEntry p4=new ProductEntry("LIGE 2020 New Fashion","","https://ae01.alicdn.com/kf/H3115826418044f7d9485305526b121ffL/LIGE-2020-New-Fashion-Mens-Watches-Top-Brand-Luxury-Automatic-Mechanical-Clock-Watch-Men-Business-Dress.jpg","780","LIGE 2020 New Fashion Mens Watches with Stainless Steel Top Brand Luxury Sports");
//        products.add(p4);
//        ProductEntry p5=new ProductEntry("Baby Boy Clothes","","https://ae01.alicdn.com/kf/HTB1GX77ck5E3KVjSZFCq6zuzXXaO/2020-3PCS-Lot-Baby-Boy-Clothes-Bodysuots-Baby-Girl-Clothes-Unicorn-Girls-Clothing-Unisex-0-12M.jpg","690","2020 3PCS/Lot Baby Boy Clothes Bodysuots Baby Girl Clothes");
//        products.add(p5);
//        ProductEntry p6=new ProductEntry("Mini WiFi Camera","","https://ae01.alicdn.com/kf/H3a23b4211e8b4af3a2d433c37e1ccdb8c/SDETER-1080P-Wireless-Mini-WiFi-Camera-Home-Security-Camera-IP-CCTV-Surveillance-IR-Night-Vision-Motion.jpg","869","DETER 1080P Wireless Mini WiFi Camera Home Security Camera");
//        products.add(p6);
//        ProductEntry p7=new ProductEntry("Office Stretch Spandex Chair","","https://www.dhresource.com/0x0/f2/albu/g12/M01/CC/91/rBVakV85M8uATphQAAXNaPzXWOY394.jpg/soft-office-stretch-spandex-chair-cover-solid.jpg","1180","Anti-dirty Computer Seat Chair Cover Removable Slipcovers For Office Seat Chairs");
//        products.add(p7);
//        ProductEntry p8=new ProductEntry("LED Solar Light Outdoor","","https://ae01.alicdn.com/kf/Ha326f01ddfc44ad99468bbabbaae8174e/100-LED-Solar-Light-Outdoor-Solar-Lamp-PIR-Motion-Sensor-Wall-Light-Waterproof-Solar-Sunlight-Powered.jpg","235","LED Solar Light Outdoor Solar Lamp PIR Motion Sensor Wall Light Waterproof Solar Sunlight Powered Garden street light");
//        products.add(p8);
//        ProductEntry p9=new ProductEntry("VOXLINK HDMI Cable","","https://ae01.alicdn.com/kf/HTB1xgA0jsLJ8KJjy0Fnq6AFDpXa4/VOXLINK-HDMI-Cable-3FT-6FT-10FT-Ultra-High-Speed-Male-to-Male-HDMI-Cable-with-Ethernet.jpg","189"," Ultra High Speed Male to Male HDMI Cable with Ethernet 1080P HDMI 1.4 4K 3D for PS3 BLURAY XBOX");
//        products.add(p9);

        // Set up the toolbar

        // Set up the RecyclerView
        recyclerView = fragmentView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        adapter = new ProductCardRecyclerViewAdapter(products);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);



        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small);
        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));

        return  fragmentView;
    }

    private void crossfade(LinearLayout destinationLayout) {
        // scrollview to destinationLayout


        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        destinationLayout.setAlpha(0f);
        destinationLayout.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        destinationLayout.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        productçgrid.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        productçgrid.setVisibility(View.GONE);
                    }
                });
    }

    private void crossfadeOut(final LinearLayout destinationLayout) {

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        productçgrid.setAlpha(0f);
        productçgrid.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        productçgrid.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        destinationLayout.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        destinationLayout.setVisibility(View.GONE);
                    }
                });
    }



    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }

        //toolbar.setNavigationOnClickListener(new NavigationIconClickListener(getContext(), view.findViewById(R.id.product_grid)));
        navigationClickListener= new NavigationIconClickListener(
                getContext(),
                view.findViewById(R.id.product_grid),
                null,
                getContext().getResources().getDrawable(R.drawable.ecom_icon_menu), // Menu open icon
                getContext().getResources().getDrawable(R.drawable.ecom_icon_menu_close));
        toolbar.setNavigationOnClickListener(navigationClickListener); // Menu close icon
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

        menuInflater.inflate(R.menu.ecom_toolbar_menu, menu);
        lmenu=menu;
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public void onItemClick(int postion) {
        ProductEntry selectedProduct= products.get(postion);
        currentViewedProduct=selectedProduct;
        productTitle.setText(selectedProduct.title);
        productPrice.setText(selectedProduct.price);
        productDescription.setText(selectedProduct.description);
        imageRequester.setImageFromUrl(productImage,selectedProduct.url);

        //to product productDestails_subview
        crossfade((LinearLayout) productDestails_subview);


    }


    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }




}
