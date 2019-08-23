package tp.custom_components;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import tp.custom_components.project.util.ByteUtil;
import tp.custom_components.project.util.CRC8;

public class CRC8Calculator2Activity extends BaseAppcompatActivity {
    private TextInputEditText etFrameHead;
    private TextInputEditText etTotalLength;
    private TextInputEditText etCommand;
    private TextInputEditText etCRC1;
    private TextInputEditText etAddress;
    private TextInputEditText etIndex1;
    private TextInputEditText etIndex2;
    private TextInputEditText etIndex3;
    private TextInputEditText etIndex4;
    private TextInputEditText etType1;
    private TextInputEditText etType2;
    private TextInputEditText etType3;
    private TextInputEditText etType4;
    private TextInputEditText etLength1;
    private TextInputEditText etLength2;
    private TextInputEditText etLength3;
    private TextInputEditText etLength4;
    private TextInputEditText etValue1;
    private TextInputEditText etValue2;
    private TextInputEditText etValue3;
    private TextInputEditText etValue4;
    private TextInputEditText etCRC2;
    private TextInputEditText etResult;
    private Toolbar toolbar;

    private LinearLayout llLTLV1;
    private LinearLayout llLTLV2;
    private LinearLayout llLTLV3;
    private LinearLayout llLTLV4;

    private TextInputLayout tilValue1;
    private TextInputLayout tilValue2;
    private TextInputLayout tilValue3;
    private TextInputLayout tilValue4;
    private boolean change = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_2crc8calculator;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = getToolbar();
        toolbar.setTitle("计算器");

        etFrameHead = findViewById(R.id.et_frame_head);
        etTotalLength = findViewById(R.id.et_total_length);
        etCommand = findViewById(R.id.et_command);
        etCRC1 = findViewById(R.id.et_crc1);
        etAddress = findViewById(R.id.et_address);
        etIndex1 = findViewById(R.id.et_index1);
        etIndex2 = findViewById(R.id.et_index2);
        etIndex3 = findViewById(R.id.et_index3);
        etIndex4 = findViewById(R.id.et_index4);
        etType1 = findViewById(R.id.et_type1);
        etType2 = findViewById(R.id.et_type2);
        etType3 = findViewById(R.id.et_type3);
        etType4 = findViewById(R.id.et_type4);
        etLength1 = findViewById(R.id.et_length1);
        etLength2 = findViewById(R.id.et_length2);
        etLength3 = findViewById(R.id.et_length3);
        etLength4 = findViewById(R.id.et_length4);
        etValue1 = findViewById(R.id.et_value1);
        etValue2 = findViewById(R.id.et_value2);
        etValue3 = findViewById(R.id.et_value3);
        etValue4 = findViewById(R.id.et_value4);
        etCRC2 = findViewById(R.id.et_crc2);
        etResult = findViewById(R.id.et_result);
        llLTLV1 = findViewById(R.id.ll_itlv1);
        llLTLV2 = findViewById(R.id.ll_itlv2);
        llLTLV3 = findViewById(R.id.ll_itlv3);
        llLTLV4 = findViewById(R.id.ll_itlv4);
        tilValue1 = findViewById(R.id.til_value1);
        tilValue2 = findViewById(R.id.til_value2);
        tilValue3 = findViewById(R.id.til_value3);
        tilValue4 = findViewById(R.id.til_value4);

        etFrameHead.addTextChangedListener(new CustomTextWatcher(etFrameHead));
        etCommand.addTextChangedListener(new CustomTextWatcher(etCommand));
        etAddress.addTextChangedListener(new CustomTextWatcher(etAddress));
        etIndex1.addTextChangedListener(new CustomTextWatcher(etIndex1));
        etIndex2.addTextChangedListener(new CustomTextWatcher(etIndex2));
        etIndex3.addTextChangedListener(new CustomTextWatcher(etIndex3));
        etIndex4.addTextChangedListener(new CustomTextWatcher(etIndex4));
        etType1.addTextChangedListener(new CustomTextWatcher(etType1));
        etType2.addTextChangedListener(new CustomTextWatcher(etType2));
        etType3.addTextChangedListener(new CustomTextWatcher(etType3));
        etType4.addTextChangedListener(new CustomTextWatcher(etType4));
        etValue1.addTextChangedListener(new CustomTextWatcher(etValue1));
        etValue2.addTextChangedListener(new CustomTextWatcher(etValue2));
        etValue3.addTextChangedListener(new CustomTextWatcher(etValue3));
        etValue4.addTextChangedListener(new CustomTextWatcher(etValue4));

        etCRC1.setOnClickListener(v -> {
                    if (!etTotalLength.getText().toString().replaceAll(" ", "").equals("")) {
                        StringBuffer crc1dat = new StringBuffer();
                        crc1dat.append(etFrameHead.getText().toString().replaceAll(" ", ""));
                        crc1dat.append(ByteUtil.toHex(Integer.parseInt(etTotalLength.getText().toString().replaceAll(" ", "")), 1));
                        crc1dat.append(etCommand.getText().toString().replaceAll(" ", ""));
                        byte crc1 = CRC8.calcCrc(ByteUtil.fromHex(crc1dat.toString()));
                        String hexcrc1 = ByteUtil.toHex(ByteUtil.byteToInt(crc1), 1);
                        etCRC1.setText(hexcrc1);
                        crc1dat.append(hexcrc1);
                        toolbar.setSubtitle(crc1dat);
                    } else {
                        Toast.makeText(this, "请将要计算CRC1的值填写完整", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        etCRC2.setOnClickListener(v -> {
            if (!etTotalLength.getText().toString().replaceAll(" ", "").equals("")) {
                StringBuffer crc2dat = new StringBuffer();
                crc2dat.append(etFrameHead.getText().toString().replaceAll(" ", ""));
                crc2dat.append(ByteUtil.toHex(Integer.parseInt(etTotalLength.getText().toString().replaceAll(" ", "")), 1));
                crc2dat.append(etCommand.getText().toString().replaceAll(" ", ""));
                byte crc1 = CRC8.calcCrc(ByteUtil.fromHex(crc2dat.toString()));
                String hexcrc1 = ByteUtil.toHex(ByteUtil.byteToInt(crc1), 1);
                etCRC1.setText(hexcrc1);
                crc2dat.append(hexcrc1);
                toolbar.setSubtitle(crc2dat);
                //*
                if (!etAddress.getText().toString().replaceAll(" ", "").equals("")) {
                    if (!etIndex1.getText().toString().replaceAll(" ", "").equals("")) {
                        crc2dat.append(etAddress.getText().toString().replaceAll(" ", ""));

                        crc2dat.append(etIndex1.getText().toString().replaceAll(" ", ""));
                        crc2dat.append(etType1.getText().toString().replaceAll(" ", ""));
                        if (!etLength1.getText().toString().replaceAll(" ", "").equals("")) {
                            crc2dat.append(ByteUtil.toHex(Integer.parseInt(etLength1.getText().toString().replaceAll(" ", "")), 1));
                        }
                        crc2dat.append(etValue1.getText().toString().replaceAll(" ", ""));


                        crc2dat.append(etIndex2.getText().toString().replaceAll(" ", ""));
                        crc2dat.append(etType2.getText().toString().replaceAll(" ", ""));
                        if (!etLength2.getText().toString().replaceAll(" ", "").equals("")) {
                            crc2dat.append(ByteUtil.toHex(Integer.parseInt(etLength2.getText().toString().replaceAll(" ", "")), 1));
                        }
                        crc2dat.append(etValue2.getText().toString().replaceAll(" ", ""));

                        crc2dat.append(etIndex3.getText().toString().replaceAll(" ", ""));
                        crc2dat.append(etType3.getText().toString().replaceAll(" ", ""));
                        if (!etLength3.getText().toString().replaceAll(" ", "").equals("")) {
                            crc2dat.append(ByteUtil.toHex(Integer.parseInt(etLength3.getText().toString().replaceAll(" ", "")), 1));
                        }
                        crc2dat.append(etValue3.getText().toString().replaceAll(" ", ""));

                        crc2dat.append(etIndex4.getText().toString().replaceAll(" ", ""));
                        crc2dat.append(etType4.getText().toString().replaceAll(" ", ""));
                        if (!etLength4.getText().toString().replaceAll(" ", "").equals("")) {
                            crc2dat.append(ByteUtil.toHex(Integer.parseInt(etLength4.getText().toString().replaceAll(" ", "")), 1));
                        }
                        crc2dat.append(etValue4.getText().toString().replaceAll(" ", ""));

                        byte crc2 = CRC8.calcCrc(ByteUtil.fromHex(crc2dat.toString()));
                        String hexcrc2 = ByteUtil.toHex(ByteUtil.byteToInt(crc2), 1);
                        etCRC2.setText(hexcrc2);
                        crc2dat.append(hexcrc2);
                        toolbar.setSubtitle(crc2dat);

                        etResult.setText(crc2dat);
                    } else {
                        Toast.makeText(this, "至少填写一个Index", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请填写地址Address", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请将要计算CRC1的值填写完整", Toast.LENGTH_SHORT).show();
            }
        });

        etResult.setOnClickListener(v -> {
            if (!etTotalLength.getText().toString().replaceAll(" ", "").equals("")) {
                StringBuffer crc2dat = new StringBuffer();
                crc2dat.append(etFrameHead.getText().toString().replaceAll(" ", ""));
                crc2dat.append(ByteUtil.toHex(Integer.parseInt(etTotalLength.getText().toString().replaceAll(" ", "")), 1));
                crc2dat.append(etCommand.getText().toString().replaceAll(" ", ""));
                byte crc1 = CRC8.calcCrc(ByteUtil.fromHex(crc2dat.toString()));
                String hexcrc1 = ByteUtil.toHex(ByteUtil.byteToInt(crc1), 1);
                etCRC1.setText(hexcrc1);
                crc2dat.append(hexcrc1);
                toolbar.setSubtitle(crc2dat);
                //*
                if (!etAddress.getText().toString().replaceAll(" ", "").equals("")) {
                    if (!etIndex1.getText().toString().replaceAll(" ", "").equals("")) {
                        crc2dat.append(etAddress.getText().toString().replaceAll(" ", ""));

                        crc2dat.append(etIndex1.getText().toString().replaceAll(" ", ""));
                        crc2dat.append(etType1.getText().toString().replaceAll(" ", ""));
                        if (!etLength1.getText().toString().replaceAll(" ", "").equals("")) {
                            crc2dat.append(ByteUtil.toHex(Integer.parseInt(etLength1.getText().toString().replaceAll(" ", "")), 1));
                        }
                        crc2dat.append(etValue1.getText().toString().replaceAll(" ", ""));


                        crc2dat.append(etIndex2.getText().toString().replaceAll(" ", ""));
                        crc2dat.append(etType2.getText().toString().replaceAll(" ", ""));
                        if (!etLength2.getText().toString().replaceAll(" ", "").equals("")) {
                            crc2dat.append(ByteUtil.toHex(Integer.parseInt(etLength2.getText().toString().replaceAll(" ", "")), 1));
                        }
                        crc2dat.append(etValue2.getText().toString().replaceAll(" ", ""));

                        crc2dat.append(etIndex3.getText().toString().replaceAll(" ", ""));
                        crc2dat.append(etType3.getText().toString().replaceAll(" ", ""));
                        if (!etLength3.getText().toString().replaceAll(" ", "").equals("")) {
                            crc2dat.append(ByteUtil.toHex(Integer.parseInt(etLength3.getText().toString().replaceAll(" ", "")), 1));
                        }
                        crc2dat.append(etValue3.getText().toString().replaceAll(" ", ""));

                        crc2dat.append(etIndex4.getText().toString().replaceAll(" ", ""));
                        crc2dat.append(etType4.getText().toString().replaceAll(" ", ""));
                        if (!etLength4.getText().toString().replaceAll(" ", "").equals("")) {
                            crc2dat.append(ByteUtil.toHex(Integer.parseInt(etLength4.getText().toString().replaceAll(" ", "")), 1));
                        }
                        crc2dat.append(etValue4.getText().toString().replaceAll(" ", ""));

                        byte crc2 = CRC8.calcCrc(ByteUtil.fromHex(crc2dat.toString()));
                        String hexcrc2 = ByteUtil.toHex(ByteUtil.byteToInt(crc2), 1);
                        etCRC2.setText(hexcrc2);
                        crc2dat.append(hexcrc2);
                        toolbar.setSubtitle(crc2dat);

                        etResult.setText(crc2dat);
                    } else {
                        Toast.makeText(this, "至少填写一个Index", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请填写地址Address", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请将要计算CRC1的值填写完整", Toast.LENGTH_SHORT).show();
            }
        });

        etValue1.setOnClickListener(v -> {
            if (!etLength1.getText().toString().replaceAll(" ", "").equals("")) {
                int len = Integer.parseInt(etLength1.getText().toString().replaceAll(" ", ""));
                etValue1.setMaxEms(len * 3 - 1);
                etValue1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(len * 3 - 1)});
                tilValue1.setCounterMaxLength(len * 3 - 1);
                //*

            }
        });


        etValue2.setOnClickListener(v -> {
            if (!etLength2.getText().toString().replaceAll(" ", "").equals("")) {
                int len = Integer.parseInt(etLength2.getText().toString().replaceAll(" ", ""));
                etValue2.setMaxEms(len * 3 - 1);
                etValue2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(len * 3 - 1)});
                tilValue2.setCounterMaxLength(len * 3 - 1);
                //*

            }
        });

        etValue3.setOnClickListener(v -> {
            if (!etLength3.getText().toString().replaceAll(" ", "").equals("")) {
                int len = Integer.parseInt(etLength3.getText().toString().replaceAll(" ", ""));
                etValue3.setMaxEms(len * 3 - 1);
                etValue3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(len * 3 - 1)});
                tilValue3.setCounterMaxLength(len * 3 - 1);
                //*

            }
        });

        etValue4.setOnClickListener(v -> {
            if (!etLength4.getText().toString().replaceAll(" ", "").equals("")) {
                int len = Integer.parseInt(etLength4.getText().toString().replaceAll(" ", ""));
                etValue4.setMaxEms(len * 3 - 1);
                etValue4.setFilters(new InputFilter[]{new InputFilter.LengthFilter(len * 3 - 1)});
                tilValue4.setCounterMaxLength(len * 3 - 1);
                //*

            }
        });


        //*
        etIndex1.setText("");
        etIndex2.setText("");
        etIndex3.setText("");
        etIndex4.setText("");
        etType1.setText("");
        etType2.setText("");
        etType3.setText("");
        etType4.setText("");
        etLength1.setText("");
        etLength2.setText("");
        etLength3.setText("");
        etLength4.setText("");
        etValue1.setText("");
        etValue2.setText("");
        etValue3.setText("");
        etValue4.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 100, 1, "增加一个ITLV");
        menu.add(1, 101, 1, "删除一个ITLV");
        menu.add(1, 102, 1, "清空ITLV的值");
        menu.add(1, 103, 1, "更换帧头");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 100:
                if (llLTLV2.getVisibility() == View.GONE) {
                    llLTLV2.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "已展开2个ITLV", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (llLTLV3.getVisibility() == View.GONE) {
                    llLTLV3.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "已展开3个ITLV", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (llLTLV4.getVisibility() == View.GONE) {
                    llLTLV4.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "已展开4个ITLV", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (llLTLV4.getVisibility() == View.VISIBLE) {
                    Toast.makeText(this, "已展开4个ITLV,已达最大值", Toast.LENGTH_SHORT).show();
                    break;
                }
                break;
            case 101:
                if (llLTLV4.getVisibility() == View.VISIBLE) {
                    llLTLV4.setVisibility(View.GONE);
                    Toast.makeText(this, "已展开3个ITLV", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (llLTLV3.getVisibility() == View.VISIBLE) {
                    llLTLV3.setVisibility(View.GONE);
                    Toast.makeText(this, "已展开2个ITLV", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (llLTLV2.getVisibility() == View.VISIBLE) {
                    llLTLV2.setVisibility(View.GONE);
                    Toast.makeText(this, "已展开1个ITLV", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (llLTLV1.getVisibility() == View.VISIBLE) {
                    Toast.makeText(this, "至少展开1个ITLV", Toast.LENGTH_SHORT).show();
                    break;
                }
                break;
            case 102:
                etIndex1.setText("");
                etIndex2.setText("");
                etIndex3.setText("");
                etIndex4.setText("");
                etType1.setText("");
                etType2.setText("");
                etType3.setText("");
                etType4.setText("");
                etLength1.setText("");
                etLength2.setText("");
                etLength3.setText("");
                etLength4.setText("");
                etValue1.setText("");
                etValue2.setText("");
                etValue3.setText("");
                etValue4.setText("");
                break;
            case 103:
                change = !change;
                if (change) {
                    etFrameHead.setText("55 aa");
                } else {
                    etFrameHead.setText("5a a5");
                }
                break;
        }
        return true;
    }
}
