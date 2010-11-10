package org.dishevelled.glazedlists.view;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import javax.swing.border.EmptyBorder;

import org.dishevelled.functor.UnaryFunction;

import org.dishevelled.layout.LabelFieldPanel;

// todo:  move to glazedlists-view-examples project
public final class Example
    extends LabelFieldPanel
    implements Runnable
{
    public Example()
    {
        super();
        setBorder(new EmptyBorder(12, 12, 12, 12));

        List<String> strings = new ArrayList<String>();
        for (int i = 0; i < 100; i++)
        {
            strings.add("Value " + i);
        }
        final EventList<String> eventList = GlazedLists.eventList(strings);
        addField("Count label:", new CountLabel(eventList));
        addField("Elements label:", new ElementsLabel(eventList));
        addLabel("Elements view:");

        UnaryFunction<String, JComponent> modelToLabel = new UnaryFunction<String, JComponent>()
        {
            // todo:  this really aught to be a list synchronized with the event list
            private final Map<String, JComponent> cache = new HashMap<String, JComponent>();

            /** {@inheritDoc} */
            public JComponent evaluate(final String element)
            {
                if (!cache.containsKey(element))
                {
                    cache.put(element, new JLabel(element));
                }
                return cache.get(element);
            }
        };

        addField(new ElementsView(eventList, modelToLabel));
        addSpacing(12);
        addLabel("Identifiable elements view:");
        addField(new IdElementsView(eventList));
        addSpacing(12);
        addLabel("Elements list:");
        addField(new ElementsList(eventList));
        addSpacing(12);
        addLabel("Identifiable elements view:");
        addFinalField(new IdElementsList(eventList));
        Timer t = new Timer(5000, new ActionListener()
            {
                public void actionPerformed(final ActionEvent e)
                {
                    eventList.remove(0);
                }
            });
        t.setRepeats(true);
        t.start();
    }

    public void run()
    {
        JFrame f = new JFrame("Example");
        f.setContentPane(this);
        f.setBounds(100, 100, 400, 800);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public static void main(final String[] args)
    {
        new Example().run();
    }
}