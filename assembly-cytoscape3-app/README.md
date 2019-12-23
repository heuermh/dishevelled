# Assembly Cytoscape3 App

## Summary

The [Assembly Cytoscape App](http://apps.cytoscape.org/apps/assembly) imports sequence
graphs in Graphical Fragment Assembly (GFA) Format versions 1.0 and 2.0 as networks into
Cytoscape, applies a visual mapping for GFA record attributes to Cytoscape node and edge
attributes, and applies a force directed graph layout.

## Graphical Fragment Assembly (GFA) 1.0 Format

The purpose of the Graphical Fragment Assembly (GFA) Format Specification version 1.0
is to capture sequence graphs as the product of an assembly, a representation of variation
in genomes, splice graphs in genes, or even overlap between reads from long-read sequencing
technology.

The GFA 1.0 format is a tab-delimited text format for describing a set of sequences and
their overlap. The first field of the line identifies the type of the line: Header lines
start with H. Segment lines start with S. Link lines start with L. A containment line starts
with C. A path line starts with P. A path traversal line starts with T.

Terminology
 * **Segment** a continuous sequence or subsequence.
 * **Link** an overlap between two segments. Each link is from the end of one segment to
 the beginning of another segment. The link stores the orientation of each segment and the
 amount of basepairs overlapping.
 * **Containment** an overlap between two segments where one is contained in the other.
 * **Path** an ordered list of traversals over oriented segments, where each consecutive pair
 of oriented segments are supported by a link record.

Tools that support GFA 1.0 format include
[ABySS](https://github.com/bcgsc/abyss),
[dsh-bio](https://github.com/heuermh/dishevelled-bio),
[seqwish](https://github.com/ekg/seqwish),
[SPAdes](http://cab.spbu.ru/software/spades), and
[vg](https://github.com/ekg/vg).

The full GFA 1.0 specification can be found at https://github.com/GFA-spec/GFA-spec/blob/master/GFA1.md.

### Importing GFA 1.0 Format

To import a sequence graph in GFA 1.0 Format into Cytoscape, use File &rarr; Import &rarr;
Network from Graphical Fragment Assembly (GFA) 1.0... and select a file.  Compressed files
(.bgz, .bzip2, .gz) are supported.

By default sequences from GFA 1.0 segment records are loaded into Cytoscape as node attributes.
To skip loading sequences, uncheck the Load sequences checkbox.

To truncate long sequences from GFA 1.0 segment records before mapping to the node label
attribute, provide a limit in base pairs (bp). The default display sequence limit is 20 bp.

By default GFA 1.0 path records are loaded into Cytoscape and displayed in a path and traversal
view. To skip loading paths, uncheck the Load paths checkbox.


## Graphical Fragment Assembly (GFA) 2.0 Format

Graphical Fragment Assembly (GFA) Format Specification version 2.0 is a generalization
of GFA that allows one to specify an assembly graph in either less detail, e.g. just the
topology of the graph, or more detail, e.g. the multi-alignment of reads giving rise to
each sequence. It is further designed to be a able to represent a string graph at any stage
of assembly, from the graph of all overlaps, to a final resolved assembly of contig paths
with multi-alignments. Apart from meeting these needs, the extensions also supports other
assembly and variation graph types.

In overview, an assembly is a graph of vertices called **segments** representing sequences
that are connected by **edges** that denote local alignments between the vertex sequences.
One can optionally specify a collection of external sequences, called **fragments**, from
which the sequence was derived (if applicable) and how they multi-align to produce the
sequence. A **gap** edge concept is also introduced so that order and orientation between
disjoint contigs of an assembly can be described. Finally, one can describe and attach a name
to any **path** or **subgraph** in the encoded string graph.

Tools that support GFA 2.0 format include
[ABySS](https://github.com/bcgsc/abyss),
[dsh-bio](https://github.com/heuermh/dishevelled-bio),
[gfakluge](https://github.com/edawson/gfakluge), and
[Gfapy](https://github.com/ggonnella/gfapy).

The full GFA 2.0 specification can be found at https://github.com/GFA-spec/GFA-spec/blob/master/GFA2.md.

### Importing GFA 2.0 Format

To import a sequence graph in GFA 2.0 Format into Cytoscape, use File &rarr; Import &rarr;
Network from Graphical Fragment Assembly (GFA) 2.0... and select a file.  Compressed files
(.bgz, .bzip2, .gz) are supported.


## Feedback, Issues, and Feature Requests

Assembly Cytoscape3 App is developed on GitHub at https://github.com/heuermh/dishevelled.

To provide feedback, report issues, or request new features, please
[create a new issue](https://github.com/heuermh/dishevelled/issues/new) or
[create a new pull request](https://github.com/heuermh/dishevelled/pulls) on Github.

Github provides a nice
[overview on how to create a pull request](https://help.github.com/articles/creating-a-pull-request).

Thank you in advance, and we appreciate your contribution!
