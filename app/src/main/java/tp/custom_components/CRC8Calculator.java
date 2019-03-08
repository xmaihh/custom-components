package tp.custom_components;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import tp.custom_components.project.util.ByteUtil;
import tp.custom_components.project.util.CRC8;

import java.nio.ByteOrder;


/**
 * Create by Administrator on 2019-3-8
 **/
public class CRC8Calculator extends AppCompatActivity implements View.OnClickListener {
    //*
    EditText et_short2bytes;
    TextView tv_short2bytes;
    //1
    EditText et_frame_head1;
    EditText et_frame_head2;
    EditText et_frame_length;
    EditText et_frame_command;
    TextView tv_result_crc1;
    //2
    EditText et_frame_crc1;
    EditText et_frame_address;
    EditText et_frame_data;
    EditText et_frame_crc2;
    TextView tv_result_crc2;

    TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crc8calculator);
        //*
        findViewById(R.id.btn_short2bytes).setOnClickListener(this);
        et_short2bytes = findViewById(R.id.et_short2bytes);
        et_short2bytes.setSelection(et_short2bytes.getText().length());
        et_short2bytes.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
        et_short2bytes.setKeyListener(DigitsKeyListener.getInstance("1234567890"));
        tv_short2bytes = findViewById(R.id.tv_short2bytes);
        //1
        findViewById(R.id.btn_calculate_crc1).setOnClickListener(this);
        et_frame_head1 = findViewById(R.id.et_frame_head1);
        et_frame_head1.setSelection(et_frame_head1.getText().length());
        et_frame_head1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
//        et_frame_head1.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefABCDEF"));

        et_frame_head2 = findViewById(R.id.et_frame_head2);
        et_frame_head2.setSelection(et_frame_head2.getText().length());
        et_frame_head2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
//        et_frame_head2.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefABCDEF"));

        et_frame_length = findViewById(R.id.et_frame_length);
        et_frame_length.setSelection(et_frame_length.getText().length());
        et_frame_length.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
//        et_frame_length.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefABCDEF"));

        et_frame_command = findViewById(R.id.et_frame_command);
        et_frame_command.setSelection(et_frame_command.getText().length());
        et_frame_command.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
//        et_frame_address.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefABCDEF"));
        tv_result_crc1 = findViewById(R.id.tv_result_crc1);
        //2
        findViewById(R.id.btn_calculate_crc2).setOnClickListener(this);
        et_frame_crc1 = findViewById(R.id.et_frame_crc1);
        et_frame_crc1.setSelection(et_frame_crc1.getText().length());
        et_frame_crc1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

        et_frame_address = findViewById(R.id.et_frame_address);
        et_frame_address.setSelection(et_frame_address.getText().length());
        et_frame_address.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

        et_frame_data = findViewById(R.id.et_frame_data);
        et_frame_data.setSelection(et_frame_data.getText().length());

        et_frame_crc2 = findViewById(R.id.et_frame_crc2);
        et_frame_crc2.setSelection(et_frame_crc2.getText().length());
        et_frame_crc2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

        tv_result_crc2 = findViewById(R.id.tv_result_crc2);

        //*3
        findViewById(R.id.btn_calculate_result).setOnClickListener(this);
        tv_result = findViewById(R.id.tv_result);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_short2bytes:
                String strshort2bytes = et_short2bytes.getText().toString().replaceAll(" ", "");
                short sdat = Short.parseShort(strshort2bytes);
                tv_short2bytes.setText("得到的结果是：\n\t(小端模式)\t" +
                        ByteUtil.toHex(ByteUtil.short2bytes(sdat, ByteOrder.LITTLE_ENDIAN))
                        + "\n\t(大端模式)\t" +
                        ByteUtil.toHex(ByteUtil.short2bytes(sdat, ByteOrder.BIG_ENDIAN))
                );
                et_frame_data.setText(ByteUtil.toHex(ByteUtil.short2bytes(sdat, ByteOrder.LITTLE_ENDIAN)));
                break;
            case R.id.btn_calculate_crc1:
                StringBuffer cdat1 = new StringBuffer();
                cdat1.append(et_frame_head1.getText().toString().replaceAll(" ", ""));
                cdat1.append(et_frame_head2.getText().toString().replaceAll(" ", ""));
                cdat1.append(et_frame_length.getText().toString().replaceAll(" ", ""));
                cdat1.append(et_frame_command.getText().toString().replaceAll(" ", ""));
                byte crc1 = CRC8.calcCrc(ByteUtil.fromHex(cdat1.toString()));
                int e = ByteUtil.byteToInt(crc1);
                tv_result_crc1.setText("得到的结果是：" + cdat1 +
                        "\n\tCRC1:\t" + ByteUtil.toHex(e, 1)
                );
                et_frame_crc1.setText(ByteUtil.toHex(e, 1));
                break;
            case R.id.btn_calculate_crc2:
                StringBuffer cdat2 = new StringBuffer();
                cdat2.append(et_frame_head1.getText().toString().replaceAll(" ", ""));
                cdat2.append(et_frame_head2.getText().toString().replaceAll(" ", ""));
                cdat2.append(et_frame_length.getText().toString().replaceAll(" ", ""));
                cdat2.append(et_frame_command.getText().toString().replaceAll(" ", ""));
                cdat2.append(et_frame_crc1.getText().toString().replaceAll(" ", ""));
                cdat2.append(et_frame_address.getText().toString().replaceAll(" ", ""));
                cdat2.append(et_frame_data.getText().toString().replaceAll(" ", ""));
//                cdat2.append(et_frame_crc2.getText().toString().replaceAll(" ", ""));
                byte crc2 = CRC8.calcCrc(ByteUtil.fromHex(cdat2.toString()));
                int x = ByteUtil.byteToInt(crc2);
                tv_result_crc2.setText("得到的结果是：" + cdat2 +
                        "\n\tCRC1:\t" + ByteUtil.toHex(x, 1)
                );
                et_frame_crc2.setText(ByteUtil.toHex(x, 1));
                break;
            case R.id.btn_calculate_result:
                StringBuffer cdat3 = new StringBuffer();
                cdat3.append(et_frame_head1.getText().toString().replaceAll(" ", ""));
                cdat3.append(et_frame_head2.getText().toString().replaceAll(" ", ""));
                cdat3.append(et_frame_length.getText().toString().replaceAll(" ", ""));
                cdat3.append(et_frame_command.getText().toString().replaceAll(" ", ""));
                cdat3.append(et_frame_crc1.getText().toString().replaceAll(" ", ""));
                cdat3.append(et_frame_address.getText().toString().replaceAll(" ", ""));
                cdat3.append(et_frame_data.getText().toString().replaceAll(" ", ""));
                cdat3.append(et_frame_crc2.getText().toString().replaceAll(" ", ""));
                byte crc = CRC8.calcCrc(ByteUtil.fromHex(cdat3.toString()));
                int ex = ByteUtil.byteToInt(crc);
                tv_result.setText("得到的结果是：" + cdat3 +
                        "\n\t" + ByteUtil.toHex(ex, 1) + "\n\n\n\n"
                );

                break;
            default:
                break;
        }
    }
}
