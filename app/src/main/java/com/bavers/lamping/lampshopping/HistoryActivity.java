package com.bavers.lamping.lampshopping;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;


public class HistoryActivity extends ParentActivity
        implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private RecyclerView recyclerView;
    private ScansAdapter scansAdapter;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // load lamps
        initializeLamps();

        // load scans
        initializeScans();

        // setup bottom navigation
        initializeBottomNavigation(R.id.action_history);

        constraintLayout = findViewById(R.id.constraint_layout);

        recyclerView = (RecyclerView) findViewById(R.id.scansRecyclerView);
        scansAdapter = new ScansAdapter(scans);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(scansAdapter);

        // adding item touch helper
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
            new RecyclerItemTouchHelper(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }


    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     * undo option will be provided in snackbar to restore the item
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ScansAdapter.ScanViewHolder) {

            // get the removed barcode to display it in snack bar
            String barcode = scans.get(viewHolder.getAdapterPosition()).barcode;

            // backup of removed item for undo purpose
            final Scan deletedItem = scans.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            scansAdapter.removeItem(viewHolder.getAdapterPosition());

            // save changed scans to file
            saveScans();

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(constraintLayout, barcode + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    scansAdapter.restoreItem(deletedItem, deletedIndex);

                    // save changed scans to file
                    saveScans();
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
