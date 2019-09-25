package inputoutput;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ByteInputOutputWindow extends JFrame {
	JButton btn1, btn2;
	
	JTextArea display;
	public ByteInputOutputWindow()
	{
		JPanel p = new JPanel();
		
		btn1 = new JButton("바이트 단위 기록");
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//기록할 내용 가져오기
				String msg = display.getText();
				//문자열을 바이트 배열로 변환
				byte[] content = msg.getBytes();
				
				try(FileOutputStream fos = new FileOutputStream("./temp.txt"))//내용을 추가하려면 true를 같이 넘겨주면 됨 
				{
					//파일에 기록
					fos.write(content);
					fos.flush();
					JOptionPane.showMessageDialog(null, "파일입력 성공!");
				} catch (Exception e1) {
					System.out.printf("%s\n", e1.getMessage());
				} 
			}
			
		});
		btn2 = new JButton("바이트 단위 읽기");
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try(FileInputStream fis = new FileInputStream("./temp.txt"))
				{
					//전체 바이트 수 구하기
					int r = fis.available();
					//데이터가 아주 많을 때는 아래처럼 한 번에 읽으면
					//메모리 부족 현상이 발생할 수 있습니다.
					//부분적으로 분할해서 읽어야 합니다.
					/*
					//데이터를 저장할 배열을 생성
					byte[] content = new byte[r];
					//데이터 읽기
					fis.read(content);
					//바이트 배열을 가지고 문자열을 만들기
					String msg = new String(content);
					display.setText(msg);
					*/
					
					//분할해서 읽을 때는 한 번에 읽을 단위를 정해서 읽으면 되는데
					// 가장 많이 사용하는 크기는 128
					//인터넷에서 하나의 패킷 크기가 1024bit - 128byte
					StringBuilder sb = new StringBuilder();
					while(true)
					{
						byte[] b = new byte[6];
						//배열을 대입해서 읽으면 읽은 바이트 수가 리턴
						int result = fis.read(b);
						//읽은 것이 없으면 반복문 중단 - 그만읽기
						if(result <= 0)
							break;
						//b에서 읽은 만큼을 가지고 문자열을 생성
						String str = new String(b,0,result);
						sb.append(str);
					}
					display.setText(sb.toString());
					
				}catch(Exception e1)
				{
					System.out.printf("%s\n", e1.getMessage());
				}
				
			}
			
		});
		
		p.add(btn1);
		p.add(btn2);
		

		
		add("North", p);
		this.setBounds(100, 100, 300, 300);
		
		display = new JTextArea(30,30);
		add(display);
		// 배치되는 컴포넌트 크기들을 가지고 프레임의 사이즈를 조정
		pack();
		this.setVisible(true);
		this.setTitle("파일 정보 출력하기");

		// 닫기 버튼을 누를 때 종료해주는 기능 추가
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}
}
