/*
 * @(#)TzfePanel.java		Created at 2014年10月14日
 * 
 * Copyright (c) 2011-2014 azolla.org All rights reserved.
 * Azolla PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
 */
package org.azolla.p.tzfe.panel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.azolla.l.ling.util.Random0;
import org.azolla.p.tzfe.cons.NumCons;
import org.azolla.p.tzfe.model.GridButton;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * The coder is very lazy for this TzfePanel class
 *
 * @author 	sk@azolla.org
 * @since 	ADK1.0
 */
public class TzfePanel extends JPanel
{

    /**
     * 
     */
    private static final long              serialVersionUID = 7581667051127144281L;

    public static final int                W                = 400;
    public static final int                H                = 400;

    public static Map<Integer, GridButton> gridButtonMap    = Maps.newHashMap();

    private static TzfePanel               instance;

    public static TzfePanel single()
    {
        return instance = null == instance ? new TzfePanel() : instance;
    }

    private TzfePanel()
    {
        super();
        initUI();
        initListener();
    }

    /**
     * The coder is very lazy for this initListener method
     *  void
     */
    private void initListener()
    {
        //not work on MAC
        //        addKeyListener(new KeyAdapter()
        //        {
        //            @Override
        //            public void keyPressed(KeyEvent e)
        //            {
        //                System.out.println("org.azolla.p.tzfe.panel.TzfePanel.initListener().new KeyAdapter() {...}.keyPressed(KeyEvent)");
        //                //do nothing
        //                boolean goodjob = false;
        //                //do nothing
        //                if(e.getKeyCode() == KeyEvent.VK_UP)
        //                {
        //                    goodjob = up();
        //                }
        //                else if(e.getKeyCode() == KeyEvent.VK_DOWN)
        //                {
        //                    goodjob = down();
        //                }
        //                else if(e.getKeyCode() == KeyEvent.VK_LEFT)
        //                {
        //                    goodjob = left();
        //                }
        //                else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        //                {
        //                    goodjob = right();
        //                }
        //
        //                if(!goodjob)
        //                {
        //                    return;
        //                }
        //
        //                //此时：要么移动好了，要么合并好了
        //                next();
        //            }
        //        });

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "VK_UP");
        getActionMap().put("VK_UP", new AbstractAction()
        {
            /**
             * 
             */
            private static final long serialVersionUID = 4819502628024769956L;

            @Override
            public void actionPerformed(ActionEvent e)
            {
                //do nothing
                doAction(KeyEvent.VK_UP);
            }
        });

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "VK_DOWN");
        getActionMap().put("VK_DOWN", new AbstractAction()
        {
            /**
             * 
             */
            private static final long serialVersionUID = -6424658478553779272L;

            @Override
            public void actionPerformed(ActionEvent e)
            {
                //do nothing
                doAction(KeyEvent.VK_DOWN);
            }
        });

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "VK_LEFT");
        getActionMap().put("VK_LEFT", new AbstractAction()
        {
            /**
             * 
             */
            private static final long serialVersionUID = -27041905896290458L;

            @Override
            public void actionPerformed(ActionEvent e)
            {
                //do nothing
                doAction(KeyEvent.VK_LEFT);
            }
        });

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "VK_RIGHT");
        getActionMap().put("VK_RIGHT", new AbstractAction()
        {
            /**
             * 
             */
            private static final long serialVersionUID = -1474793824803787971L;

            @Override
            public void actionPerformed(ActionEvent e)
            {
                //do nothing
                doAction(KeyEvent.VK_RIGHT);
            }
        });

    }

    private void doAction(int key)
    {
        //do nothing
        boolean goodjob = false;
        //do nothing
        if(key == KeyEvent.VK_UP)
        {
            goodjob = up();
        }
        else if(key == KeyEvent.VK_DOWN)
        {
            goodjob = down();
        }
        else if(key == KeyEvent.VK_LEFT)
        {
            goodjob = left();
        }
        else if(key == KeyEvent.VK_RIGHT)
        {
            goodjob = right();
        }

        if(!goodjob)
        {
            return;
        }

        //此时：要么移动好了，要么合并好了
        next();
    }

    private boolean right()
    {
        boolean movinged = false;
        boolean computinged[] = {false, false, false, false};
        GridButton prev = null;
        GridButton next = null;

        for(int y = 0; y < 4; y++)
        {
            for(int x = 0; x < 4; x++)
            {
                int unreachable = y * 4;
                for(int z = y * 4 + 3; z > unreachable; z--)
                {
                    prev = gridButtonMap.get(z);
                    next = gridButtonMap.get(z - 1);
                    if(!computinged[y] && prev.getNumber() != 0 && prev.getNumber() == next.getNumber())
                    {
                        prev.setNumber(prev.getNumber() * 2);
                        next.setNumber(0);
                        computinged[y] = true;
                        //一排全是相同数字的情况
                        if(z - 1 - 1 - 1 >= unreachable)
                        {
                            prev = gridButtonMap.get(z - 1 - 1);
                            next = gridButtonMap.get(z - 1 - 1 - 1);
                            if(prev.getNumber() != 0 && prev.getNumber() == next.getNumber())
                            {
                                prev.setNumber(prev.getNumber() * 2);
                                next.setNumber(0);
                            }
                        }
                    }
                    else if(prev.getNumber() == 0 && next.getNumber() != 0)
                    {
                        prev.setNumber(next.getNumber());
                        next.setNumber(0);
                        movinged = true;
                    }
                }
            }

        }

        return movinged || computinged[0] || computinged[1] || computinged[2] || computinged[3];
    }

    private boolean left()
    {
        boolean movinged = false;
        boolean computinged[] = {false, false, false, false};
        GridButton prev = null;
        GridButton next = null;

        for(int y = 0; y < 4; y++)
        {
            for(int x = 0; x < 4; x++)
            {
                int unreachable = y * 4 + 3;
                for(int z = y * 4; z < unreachable; z++)
                {
                    prev = gridButtonMap.get(z);
                    next = gridButtonMap.get(z + 1);
                    if(!computinged[y] && prev.getNumber() != 0 && prev.getNumber() == next.getNumber())
                    {
                        prev.setNumber(prev.getNumber() * 2);
                        next.setNumber(0);
                        computinged[y] = true;
                        //一排全是相同数字的情况
                        if(z + 1 + 1 + 1 <= unreachable)
                        {
                            prev = gridButtonMap.get(z + 1 + 1);
                            next = gridButtonMap.get(z + 1 + 1 + 1);
                            if(prev.getNumber() != 0 && prev.getNumber() == next.getNumber())
                            {
                                prev.setNumber(prev.getNumber() * 2);
                                next.setNumber(0);
                            }
                        }
                    }
                    else if(prev.getNumber() == 0 && next.getNumber() != 0)
                    {
                        prev.setNumber(next.getNumber());
                        next.setNumber(0);
                        movinged = true;
                    }
                }
            }

        }

        return movinged || computinged[0] || computinged[1] || computinged[2] || computinged[3];
    }

    private boolean down()
    {
        boolean movinged = false;
        boolean computinged[] = {false, false, false, false};
        GridButton prev = null;
        GridButton next = null;

        for(int x = 0; x < 4; x++)
        {
            for(int y = 0; y < 4; y++)
            {
                int unreachable = x;
                for(int z = 4 * 3 + x; z > unreachable; z = z - 4)
                {
                    prev = gridButtonMap.get(z);
                    next = gridButtonMap.get(z - 4);
                    if(!computinged[x] && prev.getNumber() != 0 && prev.getNumber() == next.getNumber())
                    {
                        prev.setNumber(prev.getNumber() * 2);
                        next.setNumber(0);
                        computinged[x] = true;
                        //一排全是相同数字的情况
                        if(z - 4 - 4 - 4 >= unreachable)
                        {
                            prev = gridButtonMap.get(z - 4 - 4);
                            next = gridButtonMap.get(z - 4 - 4 - 4);
                            if(prev.getNumber() != 0 && prev.getNumber() == next.getNumber())
                            {
                                prev.setNumber(prev.getNumber() * 2);
                                next.setNumber(0);
                            }
                        }
                    }
                    else if(prev.getNumber() == 0 && next.getNumber() != 0)
                    {
                        prev.setNumber(next.getNumber());
                        next.setNumber(0);
                        movinged = true;
                    }
                }
            }

        }

        return movinged || computinged[0] || computinged[1] || computinged[2] || computinged[3];
    }

    private boolean up()
    {
        boolean movinged = false;
        boolean computinged[] = {false, false, false, false};
        GridButton prev = null;
        GridButton next = null;

        for(int x = 0; x < 4; x++)
        {
            for(int y = 0; y < 4; y++)
            {
                int unreachable = 4 * 3 + x;
                for(int z = x; z < unreachable; z = z + 4)
                {
                    prev = gridButtonMap.get(z);
                    next = gridButtonMap.get(z + 4);
                    if(!computinged[x] && prev.getNumber() != 0 && prev.getNumber() == next.getNumber())
                    {
                        prev.setNumber(prev.getNumber() * 2);
                        next.setNumber(0);
                        computinged[x] = true;
                        //一排全是相同数字的情况
                        if(z + 4 + 4 + 4 <= unreachable)
                        {
                            prev = gridButtonMap.get(z + 4 + 4);
                            next = gridButtonMap.get(z + 4 + 4 + 4);
                            if(prev.getNumber() != 0 && prev.getNumber() == next.getNumber())
                            {
                                prev.setNumber(prev.getNumber() * 2);
                                next.setNumber(0);
                            }
                        }
                    }
                    else if(prev.getNumber() == 0 && next.getNumber() != 0)
                    {
                        prev.setNumber(next.getNumber());
                        next.setNumber(0);
                        movinged = true;
                    }
                }
            }

        }

        return movinged || computinged[0] || computinged[1] || computinged[2] || computinged[3];
    }

    private void next()
    {
        roundGridButton().setNumber(NumCons.roundNum());    //随机
        SwingUtilities.updateComponentTreeUI(this);
        if(isGameover())
        {
            JOptionPane.showMessageDialog(TzfePanel.single(), "Gameover!");
            reset();
        }
    }

    private void reset()
    {
        for(int i = 0; i < 16; i++)
        {
            gridButtonMap.get(i).setNumber(0);
        }
        next();
    }

    private boolean isGameover()
    {
        boolean hasFreeGrid = roundGridButton() == null ? false : true;

        int currentLocation = 0;
        int currentDown = 0;
        int currentRight = 0;
        int unreachableDown = 0;
        int unreachableRight = 0;

        GridButton prev = null;
        GridButton next = null;

        boolean hasSameGrid = false;

        for(int x = 0; x < 4 && !hasSameGrid && !hasFreeGrid; x++)  //没有空的Grid同时没有找到相同Grid
        {
            unreachableDown = 4 * 3 + x;
            for(int y = 0; y < 4; y++)
            {
                unreachableRight = y * 4 + 3;
                currentLocation = x + y * 4;
                currentDown = currentLocation + 4;
                currentRight = currentLocation + 1;

                prev = gridButtonMap.get(currentLocation);
                if(currentDown <= unreachableDown)
                {
                    next = gridButtonMap.get(currentDown);
                    if(prev.getNumber() != 0 && prev.getNumber() == next.getNumber())
                    {
                        hasSameGrid = true;
                        break;
                    }
                }
                if(currentRight <= unreachableRight)
                {
                    next = gridButtonMap.get(currentRight);
                    if(prev.getNumber() != 0 && prev.getNumber() == next.getNumber())
                    {
                        hasSameGrid = true;
                        break;
                    }
                }
            }

        }
        return !(hasFreeGrid || hasSameGrid);
    }

    /**
     * @see java.awt.Component#addMouseListener(java.awt.event.MouseListener)
     * @param l
     */
    @Override
    public synchronized void addMouseListener(MouseListener l)
    {
        //do nothing
        //        super.addMouseListener(l);
    }

    /**
     * @see java.awt.Component#addMouseMotionListener(java.awt.event.MouseMotionListener)
     * @param l
     */
    @Override
    public synchronized void addMouseMotionListener(MouseMotionListener l)
    {
        //do nothing
        //        super.addMouseMotionListener(l);
    }

    /**
     * @see java.awt.Component#addMouseWheelListener(java.awt.event.MouseWheelListener)
     * @param l
     */
    @Override
    public synchronized void addMouseWheelListener(MouseWheelListener l)
    {
        //do nothing
        //        super.addMouseWheelListener(l);
    }

    /**
     * The coder is very lazy for this initUI method
     *  void
     */
    private void initUI()
    {
        //do nothing
        setLayout(new GridLayout(4, 4, 3, 3));
        setBackground(new Color(192, 192, 192, 192));

        GridButton gridButton;
        for(int i = 0; i < 16; i++)
        {
            gridButton = new GridButton(i % 4, i / 4);
            add(gridButton);
            gridButtonMap.put(i, gridButton);
        }
        next();
    }

    private GridButton roundGridButton()
    {
        GridButton gridButton = null;
        List<GridButton> freeGridButtonList = Lists.newArrayList();
        for(int i = 0; i < 16; i++)
        {
            gridButton = gridButtonMap.get(i);
            if(gridButton.getNumber() == 0)
            {
                freeGridButtonList.add(gridButton);
            }
        }
        gridButton = null;
        if(freeGridButtonList.size() > 0)
        {
            gridButton = freeGridButtonList.get(Random0.nextRangeInt(freeGridButtonList.size()));
        }
        return gridButton;
    }

}
