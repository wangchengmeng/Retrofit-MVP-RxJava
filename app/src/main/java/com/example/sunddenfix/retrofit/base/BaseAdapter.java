package com.example.sunddenfix.retrofit.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @Author wangchengm
 * RecyclerView Adapter 的基类 增强了 可以自由添加header 和 footer功能
 */
public abstract class BaseAdapter<VH extends BaseViewHolder, T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected static final String TAG = BaseAdapter.class.getSimpleName();

    private OnItemClickListener mOnItemClickListener;

    private enum ITEM_TYPE {
        HEADER, //头部
        FOOTER, //尾部
        NORMAL, //中间
    }

    private int mLayoutId;//item的布局id
    private Context mContext;
    private List<T> mDatas; //数据载体

    private View mHeaderView;
    private View mFooterView;

    /**
     * 添加尾部
     *
     * @param footerView 尾部的View
     */
    public void addFooter(View footerView) {
        if (!hasFooter()) {
            mFooterView = footerView;
        }
    }

    /**
     * 移除尾部
     */
    public void removeFooter() {
        if (hasFooter()) {
            mFooterView = null;
        }
    }

    public List<T> getAllData() {
        return mDatas;
    }

    /**
     * 添加头部
     *
     * @param headerView 头部的View
     */
    public void addHeader(View headerView) {
        mHeaderView = headerView;
    }

    public boolean hasHeader() {
        return null != mHeaderView;
    }

    public boolean hasFooter() {
        return null != mFooterView;
    }


    @Override
    public int getItemViewType(int position) {
        int count = mDatas.size();//获取除 中间部分的数据条数
        if (hasHeader()) {
            count++; //如anjue 果带了header 那就+1
        }
        if (hasHeader() && position == 0) {
            return BaseAdapter.ITEM_TYPE.HEADER.ordinal(); //返回header类型
        } else if (hasFooter() && position == count) {
            return BaseAdapter.ITEM_TYPE.FOOTER.ordinal(); //返回footer类型
        } else {
            return BaseAdapter.ITEM_TYPE.NORMAL.ordinal(); //返回normal类型
        }
    }

    protected BaseAdapter(Context context, int layoutId, @NonNull List<T> datas) {
        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mDatas = datas;
    }

    public void setList(List<T> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }

    /**
     * 如果没有更多数据了 添加没有更多的footer
     *
     * @param hasMore true 有更多 false 没有更多
     */
    public void hasMoreData(boolean hasMore) {
        if (hasMore) {
            removeFooter();
        } else {
//            addFooter(new VRefreshFooterView(mContext, false));
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //根绝不同的类型处理对应的ViewHolder
        if (viewType == BaseAdapter.ITEM_TYPE.HEADER.ordinal()) {
            return new BaseViewHolder(mContext, mHeaderView);
        } else if (viewType == BaseAdapter.ITEM_TYPE.FOOTER.ordinal()) {
            return new BaseViewHolder(mContext, mFooterView);
        } else {
            //使用子类的ViewHolder（为了方便在子类ViewHolder里面做适配测量）
            VH viewHolder = getViewHolder(LayoutInflater.from(mContext).inflate(mLayoutId, parent, false));
            setListener(viewHolder.getContentView(), viewHolder);
            return viewHolder;
        }
    }

    protected abstract VH getViewHolder(View itemView);

    protected void setListener(final View contentView, BaseViewHolder viewHolder) {
        contentView.setOnClickListener(v -> {
            if (null != mOnItemClickListener) {
                mOnItemClickListener.onItemClick(v, viewHolder, viewHolder.getAdapterPosition());
            }
        });

        contentView.setOnLongClickListener(v -> {
            if (null != mOnItemClickListener) {
                return mOnItemClickListener.onItemLongClick(v, viewHolder, viewHolder.getAdapterPosition());
            }
            return false;
        });
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (hasHeader() && position == 0) {
            //header的逻辑分离开来，在外部处理
            return;
        }
        int count = mDatas.size();
        if (hasHeader()) {
            count++;
        }
        if (hasFooter()) {
            count++;
        }
        if (hasFooter() && position == count - 1) {
            //footer的逻辑分离开来，在外部处理
            return;
        }
        //normal的逻辑 交给实现类去处理
        if (hasHeader()) {
            onChildBindViewHolder(holder, getData(position - 1), position);
        } else {
            onChildBindViewHolder(holder, getData(position), position);
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (null != mDatas) {
            count = mDatas.size();
            if (hasHeader()) {
                count++;
            }
            if (hasFooter()) {
                count++;
            }
        }
        return count; //获取数据的数目，要加上对应的带header和footer的条数
    }

    /**
     * 获取数据对象
     *
     * @param position 列表中的位置
     * @return 返回列表中位置对应的数据
     */
    public T getData(int position) {
        if (null != mDatas && position < mDatas.size()) {
            return mDatas.get(position);
        }
        return null;
    }

    /**
     * 返回数据的条目数量
     *
     * @return 数目
     */
    public int getDataCount() {
        if (null != mDatas) {
            return mDatas.size();
        }
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    /**
     * 子类必须实现的方法，在该方法中进行数据处理以及显示
     *
     * @param holder holder对象
     * @param data   数据
     */
    protected abstract void onChildBindViewHolder(BaseViewHolder holder, T data, int position);
}