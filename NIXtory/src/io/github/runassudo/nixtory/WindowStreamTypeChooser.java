// NIXtory: DXtory for Linux.
// Copyright © 2014 RunasSudo
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

package io.github.runassudo.nixtory;

import io.github.runassudo.libnixtory.NIXtoryStream;
import io.github.runassudo.libnixtory.StreamType;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultComboBoxModel;

public class WindowStreamTypeChooser<T extends NIXtoryStream> extends JFrame {

	private static final long serialVersionUID = -8188755290868529101L;
	private JPanel contentPane;

	private StreamTypeChooserCallback<T> callback;

	/**
	 * Create the frame.
	 */
	public WindowStreamTypeChooser(
			StreamTypeChooserCallback<T> callback,
			StreamType<T>[] streamTypes) {
		setTitle("Choose Stream Type");
		this.callback = callback;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));

		final JComboBox<StreamType<T>> cbStreamType = new JComboBox<>();
		cbStreamType
				.setModel(new DefaultComboBoxModel<StreamType<T>>(streamTypes));
		contentPane.add(cbStreamType);

		JPanel panelActions = new JPanel();
		contentPane.add(panelActions);
		panelActions.setLayout(new BorderLayout(0, 0));

		JPanel panelActions2 = new JPanel();
		panelActions.add(panelActions2, BorderLayout.EAST);
		panelActions2.setLayout(new GridLayout(1, 0, 0, 0));

		JButton btnOK = new JButton("");
		btnOK.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				WindowStreamTypeChooser.this.callback
						.streamTypeChosen(((StreamType<T>) cbStreamType
								.getSelectedItem()).getStreamClass());
				WindowStreamTypeChooser.this.dispose();
			}
		});
		btnOK.setIcon(new ImageIcon(
				WindowStreamTypeChooser.class
						.getResource("/io/github/runassudo/nixtory/assets/dialog-apply.png")));
		panelActions2.add(btnOK);

		JButton btnCancel = new JButton("");
		btnCancel
				.setIcon(new ImageIcon(
						WindowStreamTypeChooser.class
								.getResource("/io/github/runassudo/nixtory/assets/window-close.png")));
		panelActions2.add(btnCancel);

		pack();
	}

	interface StreamTypeChooserCallback<T> {
		public void streamTypeChosen(Class<T> streamType);
	}
}
