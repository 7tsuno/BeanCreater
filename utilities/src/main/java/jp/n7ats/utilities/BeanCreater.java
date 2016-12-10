package jp.n7ats.utilities;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * JavaBeansとして作成されたBeanについて、一時的なインスタンス化を行わずにフィールドの値を設定したBeanを作成するためのクリエイタ.<br>
 * Stream中でのmap変換などで一時的なインスタンス化を行いたくない場合に利用する.
 *
 * <p>
 * 下記のように記述していた処理を
 *
 * <pre>
 * HogeBean bean = new HogeBean();
 * bean.setName("name");
 * bean.setId(1234);
 * </pre>
 *
 * 下記のように一文で行うことができる.
 *
 * <pre>
 * HogeBean bean = BeanCreater.of(HogeBean::new).construct(bean -> bean.setName("name"))
 *         .construct(bean -> bean.setId(1234)).build();
 * </pre>
 *
 * </p>
 *
 */
public final class BeanCreater<T> {

    /**
     * プライベートコンストラクタ. <br>
     *
     * このクラスはインスタンスを作成しない。
     */
    private BeanCreater() {
    }

    /**
     * 対象となるBean.
     */
    private T bean;

    public static <T> BeanCreater<T> of(T bean) {

        BeanCreater<T> builder = new BeanCreater<T>();
        builder.bean = bean;

        return builder;

    }

    public static <T> BeanCreater<T> of(Supplier<T> supplier) {

        return of(supplier.get());

    }

    /**
     * Beanに対する副次的な処理を実行する.<br>
     *
     * setterメソッドなどをここで実行する<br>
     *
     * <pre>
     * next(bean -> bean.setName("name"))
     * </pre>
     *
     * @param consumer
     *            副次的な処理
     * @return クリエイタ.
     */
    public BeanCreater<T> construct(Consumer<T> consumer) {

        consumer.accept(this.bean);
        return this;
    }

    /**
     * 対象となるbeanを返却する.
     *
     * @return 対象となるBean
     */
    public T build() {
        return this.bean;
    }

}
