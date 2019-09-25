package inputoutput;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class FileInfomationFrame extends JFrame {
	JButton btnInfo;
	JTextArea display;

	public FileInfomationFrame() {
		JPanel panel = new JPanel();
		JButton btnCreateFile;
		btnInfo = new JButton("파일 정보 확인");

		btnInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 파일 대화상자 만들고 출력하기
				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(false);
				int r = fc.showOpenDialog(null);
				// 확인을 선택했다면
				StringBuilder sb = new StringBuilder();
				if (r == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					sb.append("마지막 수정 날짜 : " + f.lastModified() + "\n");

					// 에폭시 타임을 Date로 변경
					Date d = new Date(f.lastModified());
					sb.append("마지막 수정 날짜 : " + d.toString() + "\n");

					// 파일 크기 출력하기
					sb.append("파일크기 : " + f.length() + "\n");// Byte단위
					long size = f.length() / 1024;
					sb.append("파일크기 : " + size + "KB\n");

					// 오늘 날짜를 이용해서 파일 이름만들기
					// 오늘날짜.log 로 만들기
					// 현재 시간을 만들기
					GregorianCalendar cal = new GregorianCalendar();
					// Date 로 변환
					Date today = new Date(cal.getTimeInMillis());
					sb.append("오늘날짜이름 : " + today.toString() + ".log\n");
					/*
					 * //파일 이름 바꾸기 File updateFile = new File("./update.dat");
					 * f.renameTo(updateFile);
					 */
					/*
					 * String name = f.getName(); String modName = name.substring(3); File fff = new
					 * File(f.getParent() + modName); f.renameTo(fff);
					 */
					// 파일 존재 여부 확인

					File ff = new File("./bin");
					if (ff.exists() == false) {
						sb.append("파일이 존재하지 않음\n");
					} else {
						sb.append("파일이 존재함");
					}
					display.setText(sb.toString());
				} else {
					display.setText("선택한 파일이 없습니다\n");
				}

			}

		});
		btnCreateFile = new JButton("파일 생성");
		btnCreateFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GregorianCalendar cal = new GregorianCalendar();
				Date today = new Date(cal.getTimeInMillis());

				Path path = Paths.get("./" + today.toString() + ".log");

				// 파일을 생성
				try {
					Files.createFile(path);
				} catch (Exception e1) {
					System.out.printf("예외 : %s\n", e1.getMessage());

				}

			}
		});

		panel.add(btnCreateFile);
		panel.add(btnInfo);
		add("North", panel);

		display = new JTextArea(30, 40);
		add(display);

		this.setBounds(100, 100, 500, 200);
		// 배치되는 컴포넌트 크기들을 가지고 프레임의 사이즈를 조정
		pack();
		this.setVisible(true);
		this.setTitle("파일 정보 출력하기");

		// 닫기 버튼을 누를 때 종료해주는 기능 추가
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}
}
