package com.example.xaydungthucdon;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    // Khai báo các view từ layout
    private EditText edtTen, edtTuoi;
    private RadioButton rdNam, rdNu, rdHdIt, rdHd13, rdHd55, rdHd67, rdHdNhieu;
    private Button btnTinh;
    private TextView thongBao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
// Ánh xạ các view từ layout
        edtTen = findViewById(R.id.edt_ten);
        edtTuoi = findViewById(R.id.edt_tuoi);
        rdNam = findViewById(R.id.rd_nam);
        rdNu = findViewById(R.id.rd_nu);
        rdHdIt = findViewById(R.id.rd_hd_it);
        rdHd13 = findViewById(R.id.rd_1_3);
        rdHd55 = findViewById(R.id.rd_5_5);
        rdHd67 = findViewById(R.id.rd_6_7);
        rdHdNhieu = findViewById(R.id.rd_hd_nhieu);
        btnTinh = findViewById(R.id.btn_tinh);
        thongBao = findViewById(R.id.thong_bao);

        // Bắt sự kiện khi bấm nút "Tính"
        btnTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tinhToanLuongCalo();
            }
        });
    }

    private void tinhToanLuongCalo() {
        // Lấy dữ liệu từ các trường nhập
        String ten = edtTen.getText().toString();
        String tuoiStr = edtTuoi.getText().toString();

        if (ten.isEmpty() || tuoiStr.isEmpty()) {
            thongBao.setText("Vui lòng nhập đầy đủ tên và tuổi.");
            return;
        }

        int tuoi = Integer.parseInt(tuoiStr);
        boolean isMale = rdNam.isChecked();
        double activityFactor = getActivityFactor();

        // Tạm thời mặc định cân nặng và chiều cao (có thể thêm chức năng nhập sau)
        double weight = 70; // cân nặng giả định
        double height = 1.75; // chiều cao giả định (m)

        // Tính BMR dựa trên công thức Harris-Benedict
        double bmr = calculateBMR(weight, height * 100, tuoi, isMale);

        // Tính TDEE
        double tdee = bmr * activityFactor;

        // Hiển thị kết quả
        thongBao.setText(String.format("Chào %s, lượng calo cần nạp mỗi ngày là: %.2f kcal", ten, tdee));
    }

    // Tính BMR dựa trên giới tính
    private double calculateBMR(double weight, double heightCm, int age, boolean isMale) {
        if (isMale) {
            return 88.362 + (13.397 * weight) + (4.799 * heightCm) - (5.677 * age);
        } else {
            return 447.593 + (9.247 * weight) + (3.098 * heightCm) - (4.330 * age);
        }
    }

    // Lấy hệ số hoạt động từ lựa chọn của người dùng
    private double getActivityFactor() {
        if (rdHdIt.isChecked()) {
            return 1.2; // Ít vận động
        } else if (rdHd13.isChecked()) {
            return 1.375; // Hoạt động 1-3 ngày/tuần
        } else if (rdHd55.isChecked()) {
            return 1.55; // Hoạt động 3-5 ngày/tuần
        } else if (rdHd67.isChecked()) {
            return 1.725; // Hoạt động 6-7 ngày/tuần
        } else if (rdHdNhieu.isChecked()) {
            return 1.9; // Hoạt động với cường độ nặng
        }
        return 1.2; // Mặc định ít vận động
    }
}